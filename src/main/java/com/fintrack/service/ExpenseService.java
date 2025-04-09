package com.fintrack.service;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.ExpenseCategory;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.BudgetRepository;
import com.fintrack.domain.repository.ExpenseRepository;
import com.fintrack.domain.repository.UserRepository;
import com.fintrack.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Transactional
    public Expense save(User user, ExpenseRequest request) {
        log.info("Adding expense = {}", request);
        Expense expense = Expense.builder()
                .user(user)
                .amount(request.getAmount())
                .date(request.getDate())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();


        // 예산 초과 감지 및 알림 전송
        YearMonth month = YearMonth.from(expense.getDate());
        budgetRepository.findByUserAndMonth(user, month).ifPresent(budget -> {
            BigDecimal totalExpense = expenseRepository.findByUserAndDateBetween(
                    user, month.atDay(1), month.atEndOfMonth()
            ).stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalExpense.compareTo(budget.getTotalBudget()) > 0) {
                notificationService.send(user, "이번 달 예산을 초과하였습니다.");
            }
        });

        return expenseRepository.save(expense);
    }

    @Transactional(readOnly = true)
    public List<Expense> getByUser(User user) {
        return expenseRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Expense> getByUserAndPeriod(User user, LocalDate start, LocalDate end) {
        return expenseRepository.findByUserAndDateBetween(user, start, end);
    }

    public List<ExpenseStatisticsResponse> getStatisticsByCategory(User user, YearMonth month) {
        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();
        Map<ExpenseCategory, BigDecimal> grouped = expenseRepository.findByUserAndDateBetween(user, from, to)
                .stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.mapping(Expense::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
        return grouped.entrySet().stream()
                .map(e -> new ExpenseStatisticsResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TotalExpenseResponse getTotalExpense(String email, YearMonth yearMonth) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BigDecimal total = expenseRepository.findTotalAmountByUserAndMonth(user, yearMonth.getYear(), yearMonth.getMonthValue());
        return new TotalExpenseResponse(total, yearMonth);
    }

    @Transactional(readOnly = true)
    public ExpenseMonthlyResponse calculateMonthlyExpense(String email, YearMonth yearMonth) {
        List<ExpenseMonthlyResponse.ExpenseMonthlyItem> items = expenseRepository
                .findByUserEmailAndDateBetween(email, yearMonth.minusMonths(5).atDay(1), yearMonth.atEndOfMonth());
        return new ExpenseMonthlyResponse(items);
    }

    @Transactional(readOnly = true)
    public CategoryRatioResponse calculateCategoryRatio(String email, YearMonth yearMonth) {
        BigDecimal total = expenseRepository.sumByUserEmailAndDateBetween(email, yearMonth.atDay(1), yearMonth.atEndOfMonth());
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return new CategoryRatioResponse(List.of());
        }

        List<CategoryRatioResponse.CategoryRatioItem> items = expenseRepository.findExpenseCategoryItems(email, yearMonth)
                .stream()
                .map(row -> new CategoryRatioResponse.CategoryRatioItem(
                        row.category(),
                        row.amount(),
                        row.amount().divide(total, 4, RoundingMode.HALF_UP).doubleValue() * 100
                ))
                .toList();
        return new CategoryRatioResponse(items);
    }

    @Transactional(readOnly = true)
    public WeeklyExpenseTrendResponse getWeeklyTrend(String email) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);
        List<WeeklyExpenseTrendResponse.DailExpense> dailyExpenses = expenseRepository
                .findExpenseDailyItems(email, weekAgo, today)
                .stream()
                .map(row -> new WeeklyExpenseTrendResponse.DailExpense(
                        row.date(),
                        row.amount()
                ))
                .toList();

        return new WeeklyExpenseTrendResponse(dailyExpenses);
    }
}

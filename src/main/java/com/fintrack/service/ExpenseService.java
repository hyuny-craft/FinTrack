package com.fintrack.service;

import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.ExpenseCategory;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.BudgetRepository;
import com.fintrack.domain.repository.ExpenseRepository;
import com.fintrack.dto.ExpenseRequest;
import com.fintrack.dto.ExpenseStatisticsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

}

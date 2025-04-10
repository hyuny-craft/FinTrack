package com.fintrack.service;

import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.Notification;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.BudgetRepository;
import com.fintrack.domain.repository.ExpenseRepository;
import com.fintrack.domain.repository.NotificationRepository;
import com.fintrack.dto.BudgetUsageRateResponse;
import com.fintrack.dto.BudgetUsageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final ExpenseRepository expenseRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Budget create(User user, YearMonth month, BigDecimal totalBudget) {
        Budget budget = new Budget(user, LocalDate.of(month.getYear(), month.getMonthValue(), 1), totalBudget, LocalDateTime.now());
        return budgetRepository.save(budget);
    }

    @Transactional(readOnly = true)
    public Budget getByUserAndMonth(User user, YearMonth month) {
        return budgetRepository.findByUserAndDateBetween(user, month.atDay(1), month.atEndOfMonth()).orElseThrow(() -> new IllegalArgumentException("Month not found"));
    }

    @Transactional(readOnly = true)
    public List<Budget> getAllByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }


    @Transactional(readOnly = true)
    public BudgetUsageResponse getBudgetUsage(String email, YearMonth month) {
        User user = userService.findByEmail(email);
        BigDecimal totalBudget = budgetRepository.findByUserAndDateBetween(user, month.atDay(1), month.atEndOfMonth())
                .map(Budget::getTotalBudget)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalExpense = expenseRepository.findByUserAndDateBetween(
                        user,
                        month.atDay(1),
                        month.atEndOfMonth()
                ).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double percentage = totalBudget.compareTo(BigDecimal.ZERO) > 0 ?
                totalExpense.divide(totalBudget, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue()
                : 0.0;

        if (totalExpense.compareTo(totalBudget) > 0) {
            String message = String.format("%s 예산을 초과했습니다. 지출: %s / 예산: %s",
                    month, totalExpense.toPlainString(), totalBudget.toPlainString());
            Notification notification = Notification.builder()
                    .user(user)
                    .message(message)
                    .sentAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);

        }

        return new BudgetUsageResponse(month.toString(), totalBudget, totalExpense, percentage);
    }

    public BudgetUsageRateResponse calculateUsageRate(String email, YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        BigDecimal budgetAmount = budgetRepository.findByUserEmailAndDateBetween(email, start, end);
        BigDecimal spentAmount = expenseRepository.sumAmountByUserEmailAndDateBetween(email, start, end);

        double usageRate = BigDecimal.ZERO.equals(budgetAmount) ? 0.0 :
                spentAmount.divide(budgetAmount, 4, RoundingMode.HALF_UP).doubleValue() * 100;
        return new BudgetUsageRateResponse(budgetAmount, spentAmount, usageRate, yearMonth);
    }
}

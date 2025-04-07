package com.fintrack.service;

import com.fintrack.domain.model.Budget;
import com.fintrack.domain.model.Expense;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.BudgetRepository;
import com.fintrack.domain.repository.ExpenseRepository;
import com.fintrack.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardResponse getMonthlySummary(User user, YearMonth month) {
        BigDecimal budget = budgetRepository.findByUserAndMonth(user, month)
                .map(Budget::getTotalBudget)
                .orElse(BigDecimal.ZERO);

        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();
        BigDecimal expense = expenseRepository.findByUserAndDateBetween(user, from, to).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResponse(budget, expense);
    }
}

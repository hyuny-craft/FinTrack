package com.fintrack.service;

import com.fintrack.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final BalanceService balanceService;

    public DashboardResponse getDashboardSummary(String email, YearMonth yearMonth) {
        TotalExpenseResponse totalExpense = expenseService.getTotalExpense(email, yearMonth);
        ExpenseMonthlyResponse monthlyExpense = expenseService.calculateMonthlyExpense(email, yearMonth);
        CategoryRatioResponse categoryRatio = expenseService.calculateCategoryRatio(email, yearMonth);
        BudgetUsageRateResponse budgetUsage = budgetService.calculateUsageRate(email, yearMonth);
        WeeklyExpenseTrendResponse weeklyTrend = expenseService.getWeeklyTrend(email);

        YearMonth start = yearMonth.minusMonths(5);
        BalanceFlowResponse balanceFlow = balanceService.getBalanceFlow(email, start, yearMonth);
        return new DashboardResponse(
                totalExpense,
                monthlyExpense,
                categoryRatio,
                budgetUsage,
                weeklyTrend,
                balanceFlow
        );
    }
}

package com.fintrack.dto;

import lombok.Builder;

@Builder
public record DashboardResponse(
        TotalExpenseResponse totalExpense,
        ExpenseMonthlyResponse monthlyExpense,
        CategoryRatioResponse categoryRatio,
        BudgetUsageRateResponse budgetUsageRate,
        WeeklyExpenseTrendResponse weeklyExpenseTrend,
        BalanceFlowResponse balanceFlow
) {
}

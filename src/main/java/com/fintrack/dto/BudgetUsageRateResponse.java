package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record BudgetUsageRateResponse(
        BigDecimal budgetAmount,
        BigDecimal spentAmount,
        double usageRate,
        YearMonth yearMonth
) {
}

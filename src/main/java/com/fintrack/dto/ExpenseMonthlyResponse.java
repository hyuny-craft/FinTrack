package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record ExpenseMonthlyResponse(
        List<ExpenseMonthlyItem> monthlyExpenses
) {
    public record ExpenseMonthlyItem(
            YearMonth yearMonth,
            BigDecimal amount
    ) {
    }
}

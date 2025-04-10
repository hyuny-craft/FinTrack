package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record ExpenseDailyResponse(
        List<ExpenseDailyItem> monthlyExpenses
) {
    public record ExpenseDailyItem(
            YearMonth yearMonth,
            BigDecimal amount
    ) {
    }
}

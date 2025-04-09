package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record WeeklyExpenseTrendResponse(
        List<DailExpense> dailExpenses
) {
    public record DailExpense(
            LocalDate date,
            BigDecimal amount
    ) {
    }
}

package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public record TotalExpenseResponse(
        BigDecimal totalAmount,
        YearMonth yearMonth) {
}

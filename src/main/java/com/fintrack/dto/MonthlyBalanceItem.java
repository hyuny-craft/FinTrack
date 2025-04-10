package com.fintrack.dto;

import java.math.BigDecimal;

public record MonthlyBalanceItem(String yearMonth, BigDecimal income, BigDecimal expense, BigDecimal balance) {
}
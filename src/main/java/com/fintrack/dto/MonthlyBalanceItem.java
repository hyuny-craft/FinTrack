package com.fintrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MonthlyBalanceItem {
    private final String yearMonth;
    private final BigDecimal income;
    private final BigDecimal expense;
    private final BigDecimal balance;
}
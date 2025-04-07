package com.fintrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DashboardResponse {
    private BigDecimal totalBudget;
    private BigDecimal totalExpense;
}

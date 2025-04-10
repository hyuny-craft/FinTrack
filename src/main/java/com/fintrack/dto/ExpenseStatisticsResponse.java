package com.fintrack.dto;

import com.fintrack.domain.model.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ExpenseStatisticsResponse {
    private ExpenseCategory category;
    private BigDecimal amount;
}

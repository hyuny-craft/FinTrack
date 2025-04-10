package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BudgetUsageResponse {
    @Schema(description = "해당 월", example = "2025-03")
    private String Month;

    @Schema(description = "예산 총액", example = "100000")
    private BigDecimal totalBudget;

    @Schema(description = "지출 총액", example = "72500")
    private BigDecimal totalExpense;

    @Schema(description = "지출 비율(%)", example = "72.5")
    private Double usedPercentage;
}

package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
public class BudgetRequest {
    @Schema(description = "사용자 이메일", example = "user@fintrack.com")
    @NotBlank
    private String email;

    @Schema(description = "월(yyyy-MM 형식)", example = "2025-03")
    @NotBlank
    private YearMonth month;

    @Schema(description = "예산 총액", example = "100000")
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal totalBudget;
}

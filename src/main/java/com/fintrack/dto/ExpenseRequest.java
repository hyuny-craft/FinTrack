package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    @Schema(description = "금액", example = "12000")
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @Schema(description = "지출 날자", example = "2025-03-23")
    @NotNull
    private LocalDate date;

    @Schema(description = "카테고리", example = "식비")
    @NotNull
    private String category;

    @Schema(description = "지출 설명", example = "점심 도시락")
    private String description;

    @Schema(description = "사용자 이메일", example = "user@fintrack.com")
    @NotNull
    private String email;
}
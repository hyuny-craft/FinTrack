package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseResponse {
    @Schema(description = "지출ID", example = "1")
    private Long id;

    @Schema(description = "금액", example = "12000")
    private BigDecimal amount;

    @Schema(description = "지출 날짜", example = "2025-03-12")
    private LocalDate date;

    @Schema(description = "카테고리", example = "식비")
    private String category;

    @Schema(description = "지출 설명", example = "점심 도시락")
    private String description;
}

package com.fintrack.dto;

import com.fintrack.domain.model.ExpenseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseListResponse {
    @Schema(description = "지출 ID", example = "1")
    private Long id;

    @Schema(description = "금액", example = "12000")
    private BigDecimal amount;

    @Schema(description = "지출 날짜", example = "2025-03-23")
    private LocalDate date;

    @Schema(description = "카테고리", example = "식비")
    private ExpenseCategory category;

    @Schema(description = "설명", example = "점심 도시락")
    private String description;
}

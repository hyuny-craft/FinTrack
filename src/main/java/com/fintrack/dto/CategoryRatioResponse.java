package com.fintrack.dto;

import java.math.BigDecimal;
import java.util.List;

public record CategoryRatioResponse(
        List<CategoryRatioItem> categoryRatios
) {
    public record CategoryRatioItem(
            String category,
            BigDecimal amount,
            double ratio
    ) {
    }
}

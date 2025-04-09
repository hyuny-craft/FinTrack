package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record BalanceFlowResponse(
        List<MonthlyBalanceItem> monthlyBalanceItems
) {
    public record MonthlyBalanceItem(
            YearMonth yearMonth,
            BigDecimal income,
            BigDecimal expense,
            BigDecimal netChange
    ) {
    }
}

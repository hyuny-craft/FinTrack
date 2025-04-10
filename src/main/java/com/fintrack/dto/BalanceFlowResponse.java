package com.fintrack.dto;

import java.util.List;

public record BalanceFlowResponse(
        List<MonthlyBalanceItem> monthlyBalanceItems
) {
}

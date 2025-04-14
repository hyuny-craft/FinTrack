package com.fintrack.api;

public record DatopRequest(
        String orgCode,
        String accountNum,
        String startDate,
        String endDate,
        String transactionType
) {
}

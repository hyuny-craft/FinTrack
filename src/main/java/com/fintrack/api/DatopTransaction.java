package com.fintrack.api;

public record DatopTransaction(
        String transactionDate,
        String amount,
        String balance,
        String description
) {
}

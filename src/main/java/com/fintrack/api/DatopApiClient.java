package com.fintrack.api;

public interface DatopApiClient {
    DatopResponse getTransactionHistory(DatopRequest request);
}

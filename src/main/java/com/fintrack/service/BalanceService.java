package com.fintrack.service;

import com.fintrack.domain.repository.TransferHistoryRepository;
import com.fintrack.dto.BalanceFlowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final TransferHistoryRepository transferHistoryRepository;

    public BalanceFlowResponse getBalanceFlow(String email, YearMonth start, YearMonth end) {
        List<BalanceFlowResponse.MonthlyBalanceItem> items = transferHistoryRepository
                .findMonthlyIncomeAndExpense(email, start, end)
                .stream()
                .map(row -> new BalanceFlowResponse.MonthlyBalanceItem(
                        row.yearMonth(),
                        row.income(),
                        row.expense(),
                        row.income().subtract(row.expense())
                ))
                .toList();

        return new BalanceFlowResponse(items);
    }
}

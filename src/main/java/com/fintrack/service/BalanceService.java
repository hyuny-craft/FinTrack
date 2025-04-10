package com.fintrack.service;

import com.fintrack.domain.repository.TransferHistoryRepository;
import com.fintrack.dto.BalanceFlowResponse;
import com.fintrack.dto.MonthlyBalanceItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {
    private final TransferHistoryRepository transferHistoryRepository;

    public BalanceFlowResponse getBalanceFlow(String email, YearMonth start, YearMonth end) {
        List<MonthlyBalanceItem> items =
                transferHistoryRepository
                        .findMonthlyBalanceItemsByUserEmailAndDateBetween(email, start.atDay(1), end.atEndOfMonth())
                        .stream()
                        .map(row -> new MonthlyBalanceItem(
                                        row.yearMonth(),
                                        row.income(),
                                        row.expense(),
                                        row.balance()
                                )
                        )
                        .toList();

        return new BalanceFlowResponse(items);
    }
}

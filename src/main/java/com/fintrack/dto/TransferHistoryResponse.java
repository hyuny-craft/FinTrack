package com.fintrack.dto;

import com.fintrack.domain.model.TransferType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransferHistoryResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private TransferType type;
    private String bankName;
    private String accountNumber;
}

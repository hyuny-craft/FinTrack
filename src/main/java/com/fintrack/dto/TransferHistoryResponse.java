package com.fintrack.dto;

import com.fintrack.domain.model.TransferType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransferHistoryResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransferType type;
    private String bankName;
    private String accountNumber;
}

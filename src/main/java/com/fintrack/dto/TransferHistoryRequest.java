package com.fintrack.dto;

import com.fintrack.domain.model.TransferType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransferHistoryRequest {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    private TransferType type;

    @NotBlank
    private String bankName;

    @NotBlank
    private String accountNumber;
}

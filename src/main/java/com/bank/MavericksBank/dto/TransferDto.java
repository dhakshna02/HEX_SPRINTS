package com.bank.MavericksBank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferDto(

        @NotNull
        long sourceAccount,

        @NotNull
        String destinationAccount,

        @NotNull
        @Positive
        BigDecimal amount,

        String remarks,

        @NotNull
        String ifsc
) {
}

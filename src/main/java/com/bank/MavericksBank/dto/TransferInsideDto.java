package com.bank.MavericksBank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferInsideDto(

        @NotNull
        long sourceAccount,

        @NotNull
        long destinationAccount,

        @NotNull
        @Positive
        BigDecimal amount,

        String remarks
) {
}

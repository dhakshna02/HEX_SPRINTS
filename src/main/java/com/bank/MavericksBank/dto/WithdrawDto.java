package com.bank.MavericksBank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawDto(
        @NotNull
        long accountId,
        @NotNull
        @Positive
        BigDecimal withdrawValue,

        String remarks
) {
}

package com.bank.MavericksBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositDto(

        @NotNull
        long accountId,

        @NotNull
        @Positive
        BigDecimal value,

        String remarks
) {
}

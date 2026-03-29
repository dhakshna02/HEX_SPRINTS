package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;

public record CreateLoanDto(
        AccountType accountType,
        BigDecimal amount,
        long customerId
) {
}

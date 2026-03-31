package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountInfoDto(
        long AccountNumber,
        String customerName,
        AccountType accountType,
        BigDecimal balance,
        AccountStatus accountCurrentStatus,
        LocalDate openDate,
        long verifierId,
        String accountVerifierName


) {
}

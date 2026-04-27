package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountInfoDtos(
        long AccountNumber,
        String customerName,
        AccountType accountType,
        BigDecimal balance,
        AccountStatus accountCurrentStatus,
        LocalDate openDate,
        String branchName,
        String IFSC,
        String accountOpeningStatus



) {
}

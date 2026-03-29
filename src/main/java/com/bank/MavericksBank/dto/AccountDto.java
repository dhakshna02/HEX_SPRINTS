package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountDto(


        AccountType accountType,
        long customerId


) {
}

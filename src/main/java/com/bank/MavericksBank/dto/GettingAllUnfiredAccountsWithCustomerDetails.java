package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GettingAllUnfiredAccountsWithCustomerDetails(

        long AccountNumber,
        AccountType accountType,
        BigDecimal balance,
        LocalDate AccountOpeningDate,
        AccountOpeningStatus accountOpeningStatus,
        String CustomerName

) {
}

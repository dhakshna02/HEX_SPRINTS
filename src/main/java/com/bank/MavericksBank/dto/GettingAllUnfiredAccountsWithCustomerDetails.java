package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GettingAllUnfiredAccountsWithCustomerDetails(

        long cccountNumber,
        AccountType accountType,
        BigDecimal balance,
        LocalDate acccountOpeningDate,
        AccountOpeningStatus accountOpeningStatus,
        String customerName,
        String identityProof,
        String addressProof,
        String panNo

) {
}

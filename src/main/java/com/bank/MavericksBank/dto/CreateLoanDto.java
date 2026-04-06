package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.CollatralTypes;
import com.bank.MavericksBank.enums.LoanType;

import java.math.BigDecimal;

public record CreateLoanDto(
        LoanType loanType, // to be stored in loan
        BigDecimal requestedLoanAmount,
        long incomeCertificate // to be stored in customer



) {
}

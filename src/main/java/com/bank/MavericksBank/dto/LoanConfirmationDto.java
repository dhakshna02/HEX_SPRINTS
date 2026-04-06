package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.CustomerLoanDecision;
import com.bank.MavericksBank.enums.LoanConfirmation;

public record LoanConfirmationDto(
        long loanId,
        CustomerLoanDecision loanConfirmation
) {
}

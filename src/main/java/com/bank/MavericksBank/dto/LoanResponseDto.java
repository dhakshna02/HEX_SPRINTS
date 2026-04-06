package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.enums.LoanType;

import java.math.BigDecimal;

public record LoanResponseDto(
        long loanId,
        LoanType loanType,
        BigDecimal requestedLoanAmount,
        BigDecimal approvedLoanAmount,
        int months,
        BigDecimal emi,
        LoanStatus loanStatus


) {
}

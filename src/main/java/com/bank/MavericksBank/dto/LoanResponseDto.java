package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.CollatralStatus;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.enums.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponseDto(
        long loanId,
        LoanType loanType,
        BigDecimal requestedLoanAmount,
        BigDecimal approvedLoanAmount,
        int months,
        BigDecimal emi,
        LoanStatus loanStatus,
        BigDecimal intrestRate,
        BigDecimal LoanBalance,
        LocalDate LoanStatedAt,
        String name





) {
}

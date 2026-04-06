package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.LoanStatus;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public record VerifyTheLoanDto(

        BigDecimal approvedLoanAmount,
        BigDecimal emi,
        BigDecimal intrestRate,
        int monthsOfEmi,
        LoanStatus loanStatus,
        long loanId
) {
}

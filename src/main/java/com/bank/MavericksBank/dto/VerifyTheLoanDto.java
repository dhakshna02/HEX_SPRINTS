package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.LoanStatus;

import java.math.BigDecimal;

public record VerifyTheLoanDto(

        BigDecimal emi,
        BigDecimal intrestRate,
        int monthsOfEmi,
        LoanStatus loanStatus,
        long employeeId,
        long loanId
) {
}

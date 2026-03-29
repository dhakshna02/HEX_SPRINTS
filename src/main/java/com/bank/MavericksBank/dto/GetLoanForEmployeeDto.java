package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.LoanStatus;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

public record GetLoanForEmployeeDto(

        long Loanid,
        AccountType accountType,
        BigDecimal LoanAmount,
        BigDecimal intrestRate,
        int LoanTotalMonths,
        BigDecimal emi,
        LoanStatus loanStatus,
        long customerId

) {
}

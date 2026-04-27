package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanExistingDto(
    long loanId,
    String loanType,
    BigDecimal approvedLoanAmount,
    String loanStatus,
    BigDecimal loanBalance,
    BigDecimal emi,
    LocalDate createdAt

) {
}

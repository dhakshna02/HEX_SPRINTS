package com.bank.MavericksBank.dto;

public record AssignEmpsToLoanDto(
        long loanId,
        long loanVerifier,
        long assestVerifier,
        long finaincialAnalyst
) {
}

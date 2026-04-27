package com.bank.MavericksBank.dto;

public record LoanRemarksDto(
        String remarks,
        long loanId,
        String CollatralStatus
) {
}

package com.bank.MavericksBank.dto;

public record ManagerStatDto(
        int NoloanOngoing,
        int NoLoanPending,
        int NoOfAcctActive,
        int NoOfAccPending
) {
}

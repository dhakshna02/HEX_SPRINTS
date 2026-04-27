package com.bank.MavericksBank.dto;

import java.util.List;

public record LoansManagerDto(
        List<LoansFotManagerDto> loans,
        int totalPages,
        long totalElements
) {
}

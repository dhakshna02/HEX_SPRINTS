package com.bank.MavericksBank.dto;

import java.util.List;

public record GetLoansForFinAnalystDto(
        List<getAllLoansForFinancialAnalyst> loans,
        int totalPages,
        long totalElements

) {
}

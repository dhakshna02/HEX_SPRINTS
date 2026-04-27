package com.bank.MavericksBank.dto;

import java.util.List;

public record GetAllLoansDtoForPagination(
        List<GetLoanForEmployeeDto> loans,
        int totalPages,
        long totalElements
) {
}

package com.bank.MavericksBank.dto;

import java.util.List;

public record BenificaryReDto(
        List<BenificaryResponseDto> benifiacries,
        int totalPages,
        long totalElements
) {
}

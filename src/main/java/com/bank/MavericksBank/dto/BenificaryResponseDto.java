package com.bank.MavericksBank.dto;

public record BenificaryResponseDto(
        long id,
        String ifsc,
        long accountNumber,
        String payeeName


) {
}

package com.bank.MavericksBank.dto;

public record SignatureAndAccountTypeDto(
        String accountType,
        String signature
) {
}

package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.Role;

public record ViewRemarksDto(
        Role  role,
        String remarks
) {
}

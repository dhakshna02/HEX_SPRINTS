package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.Designation;

public record EmployeeDto(
        String name,
        String email,
        String mobNo,

        Designation designation
) {
}

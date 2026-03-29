package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.Designation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeSignUpDto(
        @NotNull
        @NotBlank
        @Size(min=3 , max = 255)
        String name,

        @NotNull
        @NotBlank
        @Size(min=3 , max = 255)
        String email,

        @NotNull
        @NotBlank
        @Size(min=3 , max = 30)
        String mobNo,

        Designation designation,

        String userName,

        String password

) {
}


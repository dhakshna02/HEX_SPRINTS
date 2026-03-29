package com.bank.MavericksBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerSignUpDto(
        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String name,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 30)
        String mobNumber,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String mailId,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 1000)
        String address,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String panNo,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String aadharNo,

        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String userName,

        @NotBlank
        @NotNull
        String password




) {
}

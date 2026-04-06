package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

        LocalDate DOB,


        Gender gender,

        String occupation,

        long annualIncome,



        @NotBlank
        @NotNull
        @Size(min = 3 , max = 255)
        String userName,

        @NotBlank
        @NotNull
        String password




) {
}

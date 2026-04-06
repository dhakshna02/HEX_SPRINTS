package com.bank.MavericksBank.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerDto(

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
        String address


) {
}



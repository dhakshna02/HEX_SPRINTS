package com.bank.MavericksBank.dto;

public record BenficaryAddDto(

        String ifsc,
        long accountNumber,
        String PayeeName

) {
}

package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.Gender;

public record LoanCustomerDto(
        String name ,
        String mailId,
        String address,
        Gender gender,
        String occupation,
        long annualIncome,
        String identityProof,
        String addressProof,
        String panNo,
        String incomeCertificate,
        String photograph

) {
}

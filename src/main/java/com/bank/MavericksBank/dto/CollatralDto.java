package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.CollatralTypes;

public record CollatralDto(
        long LoanId,
        String collatralName,
        CollatralTypes collatralTypes,
        String collatralAddress
) {
}

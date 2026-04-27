package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.CollatralTypes;

import java.math.BigDecimal;

public record CollatralResponseDto(
        long LoanId,
        long collatralId,
        String collatralName,
        CollatralTypes collatralTypes,
        String collatralAddress,
        String collatralDocument
) {
}

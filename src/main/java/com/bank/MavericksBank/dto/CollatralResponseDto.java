package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.CollatralTypes;

import java.math.BigDecimal;

public record CollatralResponseDto(
        long LoanId,
        String collatralName,
        CollatralTypes collatralTypes,
        BigDecimal collatralValye,
        String collatralAddress
) {
}

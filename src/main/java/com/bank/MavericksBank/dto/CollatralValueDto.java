package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record CollatralValueDto(
        long loanId,
        long collatralId,
        BigDecimal collatralValue
) {
}

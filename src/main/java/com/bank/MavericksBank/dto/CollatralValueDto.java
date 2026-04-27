package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record CollatralValueDto(

        long collatralId,
        BigDecimal collatralValue
) {
}

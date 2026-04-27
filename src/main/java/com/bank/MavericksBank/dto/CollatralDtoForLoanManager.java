package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record CollatralDtoForLoanManager(
        String collatralName,
        String collatralType,
        BigDecimal value
) {
}

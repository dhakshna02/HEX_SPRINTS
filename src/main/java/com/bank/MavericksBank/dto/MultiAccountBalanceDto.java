package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record MultiAccountBalanceDto(
        long accountId,
        BigDecimal AccountBalance
) {
}

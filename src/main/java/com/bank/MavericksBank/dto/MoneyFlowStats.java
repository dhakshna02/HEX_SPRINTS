package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MoneyFlowStats(
        String name,
        BigDecimal inflow,
        BigDecimal outflow,
        BigDecimal inflowLastMonth,
        BigDecimal outflowLastMonth
) {
}

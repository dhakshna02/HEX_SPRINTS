package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record InflowOutFlowDto(
        BigDecimal totalBalance,
        BigDecimal outFlow,
        BigDecimal inFlow,
        String name,
        BigDecimal inflowLatMonth,
        BigDecimal outFlowLastMonth
) {
}

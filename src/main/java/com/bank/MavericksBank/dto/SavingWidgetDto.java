package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record SavingWidgetDto(
        BigDecimal savings,
        int accounts
) {
}

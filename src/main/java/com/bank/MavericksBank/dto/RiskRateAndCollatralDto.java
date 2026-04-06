package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.RiskRating;

import java.math.BigDecimal;

public record RiskRateAndCollatralDto(
        long loanId,
        RiskRating riskRating

) {
}

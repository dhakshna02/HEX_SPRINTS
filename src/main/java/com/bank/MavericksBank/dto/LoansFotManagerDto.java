package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record LoansFotManagerDto(

        long loanId,
        String loanType,
        String status,
        BigDecimal requestAmount
) {
}

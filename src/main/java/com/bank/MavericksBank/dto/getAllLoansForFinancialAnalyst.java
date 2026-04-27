package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record getAllLoansForFinancialAnalyst(

        long id,
        String loanType,
        BigDecimal requestLoanAmount,
        String loanStatus
) {
}

package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.util.List;

public record LoanDetailsForFinancialAnalystDtoById(
        long loanId,
        String loanType,
        BigDecimal requestLoanAmount,
        String incomeCertificate,
        List<LoanExistingDto> otherLoans

) {
}

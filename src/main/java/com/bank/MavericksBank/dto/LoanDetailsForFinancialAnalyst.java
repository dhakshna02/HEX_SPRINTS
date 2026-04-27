package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.util.List;

public record LoanDetailsForFinancialAnalyst(
        long loanId,
        String laonType,
        BigDecimal  requestedLoanAmount,
        String loanStatus,
        String annualIncome,
        List<EmpOtherLoanDetails> otherLoanDetails

) {
}

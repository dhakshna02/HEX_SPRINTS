package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.util.List;

public record LoanDtoForLoanManager(
        List<CollatralDtoForLoanManager> collatrals,
        long loanId,
        BigDecimal requestedLoanAmount,
        String loanStatus,
        String loantype,
        String riskRate,
        String customerName,
        String address,
        String gender,
        String occupation,
        String IncomeCertificate

) {
}

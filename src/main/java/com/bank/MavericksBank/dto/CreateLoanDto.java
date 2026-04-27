package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.CollatralTypes;
import com.bank.MavericksBank.enums.LoanType;
import com.bank.MavericksBank.model.Collatral;

import java.math.BigDecimal;

public record CreateLoanDto(
        String loanType, // to be stored in loan
        BigDecimal requestedLoanAmount,
        String incomeCertificate, // to be stored in customer

        // adding collatral for loans

        String collatralName,

        String collatralType,

        String collatralAddress,

        String collatralDocument



) {
}

package com.bank.MavericksBank.dto;

import java.math.BigDecimal;

public record LoanWidgetDto(
        BigDecimal loanValue,
        int no_of_Loans
) {
}

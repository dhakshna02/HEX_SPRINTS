package com.bank.MavericksBank.dto;

import java.util.List;

public record GetAllAccountsWithUSerNameDetailsForManagerDto(
        List<GettingAllUnfiredAccountsWithCustomerDetails> accounts,
        int totalPages,
        long totalElements
) {
}

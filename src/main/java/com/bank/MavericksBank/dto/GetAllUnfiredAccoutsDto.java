package com.bank.MavericksBank.dto;

import java.util.List;

public record GetAllUnfiredAccoutsDto(
        List<GettingAllUnfiredAccountsWithCustomerDetails> accounts,
        int totalPages,
        long totalElements
        ) {
}

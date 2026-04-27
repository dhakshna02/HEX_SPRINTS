package com.bank.MavericksBank.dto;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.model.Employees;

public record AccountVerificationDto(
        AccountOpeningStatus accountApprovedStatus,
        long accountid

        ) {
}

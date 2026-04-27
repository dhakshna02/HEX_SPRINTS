package com.bank.MavericksBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetAllTranscationDto(

        long transcationId,
        LocalDateTime date,
        String type,
        String description,
        BigDecimal amount,
        BigDecimal balanceAfterTranscation,
        String TreanscationFlow,
        long accountId

) {
}

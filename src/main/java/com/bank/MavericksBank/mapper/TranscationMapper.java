package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.GetAllTranscationDto;
import com.bank.MavericksBank.model.Transactions;

public class TranscationMapper {
    public static GetAllTranscationDto TransEntToDto(Transactions transactions) {
        return  new GetAllTranscationDto(
                transactions.getId(),
                transactions.getCreatedAt(),
                transactions.getTranscationType().toString(),
                transactions.getRemarks(),
                transactions.getAmount(),
                transactions.getBalanceAfterTranscation(),
                transactions.getTranscationflow().toString(),
                transactions.getSourceAccount().getId()
        );
    }
}

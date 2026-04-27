package com.bank.MavericksBank.dto;

import java.util.List;

public record getAllTranscationssDto(
        List<GetAllTranscationDto> transcations,
        int totalPages,
        long totalElements
) {
}

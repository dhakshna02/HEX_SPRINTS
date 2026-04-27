package com.bank.MavericksBank.dto;

import org.antlr.v4.runtime.atn.AmbiguityInfo;

import java.util.List;

public record EmpIdsDto(
        List<ManagerDto> loanVerifierId,
        List<ManagerDto> assetVErifierId,
        List<ManagerDto> financialAnalystId
) {
}

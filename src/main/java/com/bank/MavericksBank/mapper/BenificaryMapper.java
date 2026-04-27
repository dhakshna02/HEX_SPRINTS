package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.BenficaryAddDto;
import com.bank.MavericksBank.dto.BenificaryResponseDto;
import com.bank.MavericksBank.model.Benificaries;

public class BenificaryMapper {
    public static Benificaries DtoToEnt(BenficaryAddDto dto) {
        Benificaries benificaries = new Benificaries();
        benificaries.setIFSC(dto.ifsc());
        benificaries.setAccountNumber(dto.accountNumber());
        benificaries.setPayeeName(dto.PayeeName());
        return benificaries;
    }

    public static BenificaryResponseDto EntToDto(Benificaries benificaries) {
        return  new BenificaryResponseDto(

                benificaries.getId(),
                benificaries.getIFSC(),
                benificaries.getAccountNumber(),
                benificaries.getPayeeName()
        );
    }
}

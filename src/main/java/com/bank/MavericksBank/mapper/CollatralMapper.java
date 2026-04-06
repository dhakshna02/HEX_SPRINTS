package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.model.Collatral;
import com.bank.MavericksBank.model.Loans;

public class CollatralMapper {
    public static Collatral CollatralDtoToEnt(CollatralDto collatralDto) {

        Collatral collatral = new Collatral();

        Loans loans = new Loans();
        loans.setId(collatralDto.LoanId());
        collatral.setLoans(loans);
        collatral.setCollataralname(collatralDto.collatralName());
        collatral.setCollatralType(collatralDto.collatralTypes());
        collatral.setCollatralAddress(collatralDto.collatralAddress());

        return collatral;
    }
}

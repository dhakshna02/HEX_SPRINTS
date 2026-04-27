package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.dto.CollatralDtoForLoanManager;
import com.bank.MavericksBank.dto.CreateLoanDto;
import com.bank.MavericksBank.enums.CollatralTypes;
import com.bank.MavericksBank.model.Collatral;
import com.bank.MavericksBank.model.Loans;
import jakarta.validation.Valid;

public class CollatralMapper {
    public static Collatral CollatralDtoToEnt(CollatralDto collatralDto) {

        Collatral collatral = new Collatral();

        Loans loans = new Loans();
        loans.setId(collatralDto.LoanId());
        collatral.setLoans(loans);
        collatral.setCollataralname(collatralDto.collatralName());
        collatral.setCollatralType(collatralDto.collatralTypes());
        collatral.setCollatralAddress(collatralDto.collatralAddress());
        collatral.setCollatralDocument(collatralDto.collatralDocument());

        return collatral;
    }

    public static Collatral CollatralDtooInLoanToEnity(@Valid CreateLoanDto createLoanDto) {

        Collatral collatral = new Collatral();

        collatral.setCollataralname(createLoanDto.collatralName());
        collatral.setCollatralType(CollatralTypes.valueOf(createLoanDto.collatralType()));
        collatral.setCollatralAddress(createLoanDto.collatralAddress());
        collatral.setCollatralDocument(createLoanDto.collatralDocument());


        return  collatral;

    }


    public static CollatralDtoForLoanManager CollatralEntToDtoForManager(Collatral collatral) {
        return new CollatralDtoForLoanManager(
                collatral.getCollataralname(),
                collatral.getCollatralType().toString(),
                collatral.getCollatralValye()
        );
    }
}

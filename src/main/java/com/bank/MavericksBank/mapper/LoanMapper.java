package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.LoanType;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Loans;
import jakarta.validation.Valid;

import java.time.LocalDate;

public class LoanMapper {
    public static Loans LoanDtoToEntity(@Valid CreateLoanDto createLoanDto) {
    Loans loans = new Loans();
    loans.setLoanType(LoanType.valueOf(createLoanDto.loanType()));
    loans.setRequestedLoanAmount(createLoanDto.requestedLoanAmount());

    return loans;
    }

    public static Loans VerifyLoanDtoToEntity(Loans loans,@Valid VerifyTheLoanDto verifyTheLoanDto) {

        loans.setApprovedLoanAmount(verifyTheLoanDto.approvedLoanAmount());
        loans.setEmi(verifyTheLoanDto.emi());
        loans.setIntrestRate(verifyTheLoanDto.intrestRate());
        loans.setMonths(verifyTheLoanDto.monthsOfEmi());
        loans.setLoanStatus(verifyTheLoanDto.loanStatus());
        loans.setId(verifyTheLoanDto.loanId());
        loans.setApprovedAt(LocalDate.now());
        return loans;
    }

    public  static GetLoanForEmployeeDto EntityToDto(Loans loans){
        return new GetLoanForEmployeeDto(
                loans.getId(),
                loans.getLoanType(),
                loans.getRequestedLoanAmount(),
                loans.getIntrestRate(),
                loans.getMonths(),
                loans.getEmi(),
                loans.getLoanStatus(),
                (int) loans.getCustomers().getId()
        );
    }

    public static getAllLoansForFinancialAnalyst FinLoanToDto(Loans loans) {

        return  new getAllLoansForFinancialAnalyst(
                loans.getId(),
                loans.getLoanType().toString(),
                loans.getRequestedLoanAmount(),
                loans.getLoanStatus().toString()
        );
    }

    public static LoanExistingDto existingLoanEntToDto(Loans loans) {
        return  new LoanExistingDto(
                loans.getId(),
                loans.getLoanType().toString(),
               loans.getApprovedLoanAmount(),
                loans.getLoanStatus().toString(),
                loans.getLoanBalance(),
                loans.getEmi(),
                loans.getApprovedAt()
        );
    }

    public static LoansFotManagerDto manEntToDto(Loans loans) {

        return  new LoansFotManagerDto(
                loans.getId(),
                loans.getLoanType().toString(),
                loans.getLoanStatus().toString(),
                loans.getRequestedLoanAmount()
        );
    }

    public static GetLoanForEmployeeDto LoanEntToDto(Loans loans) {

        return  new GetLoanForEmployeeDto(
                loans.getId(),
                loans.getLoanType(),
                loans.getRequestedLoanAmount(),
                loans.getIntrestRate(),
                loans.getMonths(),
                loans.getEmi(),
                loans.getLoanStatus(),
                loans.getCustomers().getId()
        );
    }

//    public static LoanResponseDto EntToDto(Loans loans) {
//        return new LoanResponseDto(
//                loans.getId(),
//                loans.getLoanType().toString(),
//                loans.getRequestedLoanAmount(),
//                loans.getApprovedLoanAmount(),
//                loans.getMonths(),
//                loans.getEmi(),
//                loans.getLoanStatus().toString(),
//                loans.getIntrestRate(),
//                loans.getLoanBalance(),
//                loans.getApprovedAt(),
//                loans.getCustomers().getName(),
//                loans.getCollatralStatus().toString()
//        );
//    }
}

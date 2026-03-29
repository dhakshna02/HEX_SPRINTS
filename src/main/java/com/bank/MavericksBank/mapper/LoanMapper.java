package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.CreateLoanDto;
import com.bank.MavericksBank.dto.GetLoanForEmployeeDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Loans;
import jakarta.validation.Valid;

public class LoanMapper {
    public static Loans LoanDtoToEntity(@Valid CreateLoanDto createLoanDto) {
    Loans loans = new Loans();
    loans.setAccountType(createLoanDto.accountType());
    loans.setAmount(createLoanDto.amount());

    return loans;
    }

    public static Loans VerifyLoanDtoToEntity(Loans loans,@Valid VerifyTheLoanDto verifyTheLoanDto) {

        loans.setEmi(verifyTheLoanDto.emi());
        loans.setIntrestRate(verifyTheLoanDto.intrestRate());
        loans.setMonths(verifyTheLoanDto.monthsOfEmi());
        loans.setLoanStatus(verifyTheLoanDto.loanStatus());
        loans.setId(verifyTheLoanDto.loanId());
        return loans;
    }

    public  static GetLoanForEmployeeDto EntityToDto(Loans loans){
        return new GetLoanForEmployeeDto(
                loans.getId(),
                loans.getAccountType(),
                loans.getAmount(),
                loans.getIntrestRate(),
                loans.getMonths(),
                loans.getEmi(),
                loans.getLoanStatus(),
                (int) loans.getCustomers().getId()
        );
    }
}

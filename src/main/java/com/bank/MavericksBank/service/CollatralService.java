package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.dto.CollatralValueDto;
import com.bank.MavericksBank.dto.CreateLoanDto;
import com.bank.MavericksBank.enums.Designation;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.mapper.CollatralMapper;
import com.bank.MavericksBank.model.Collatral;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.CollatralRepository;
import com.bank.MavericksBank.repository.EmployeeRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CollatralService {
    private final CollatralRepository collatralRepository;
    private  final UsersService usersService;
    private final LoanRepository loanRepository;
    private final EmployeeRepository employeeRepository;

    public void addCollatral(CollatralDto collatralDto, String name) {


        Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(collatralDto.LoanId());
        Loans loans = loanRepository.getById(collatralDto.LoanId());
        System.out.println(loans);
        if(!loans.getCustomers().getUsers().getUsername().equals(users.getUsername()))
            throw new AccountRemarksException("This acoount is not hold by thsi user");
        if(!loans.getLoanStatus().equals(LoanStatus.PENDING))
            throw new AccountRemarksException("This account already onGoing so collatral cant be added");



        Collatral collatral = CollatralMapper.CollatralDtoToEnt(collatralDto);
        collatralRepository.save(collatral);


    }

    public CollatralResponseDto getCollatrals(long lid, String name) {

        Loans loans = loanRepository.findById(lid).orElseThrow(()->new AccountRemarksException("Account is invalid"));

        Employees emp = employeeRepository.findById(loans.getAssestVerifierId()).orElseThrow(()->new AccountRemarksException("invalid user"));
        if(!emp.getDesignation().equals(Designation.ASSET_VERIFIER))
            throw  new AccountRemarksException("Account is not hold by this employee");


        return collatralRepository.getAllCollatralsById(lid);

    }

    public void addCollatralValue(CollatralValueDto collatralValueDto, String name) {

   Users users = (Users) usersService.loadUserByUsername(name);



   // need to chanfge the user table - if time perimists to that dhakshna dont forget

        Collatral collatral = collatralRepository.findById(collatralValueDto.collatralId()).orElseThrow(()->new AccountRemarksException("No collatral exist"));


        collatral.setCollatralValye(collatralValueDto.collatralValue());
        collatralRepository.save(collatral);
   }

    public void addCollatralInCreateLoan(@Valid CreateLoanDto createLoanDto, Loans loans) {


        Collatral collatral = CollatralMapper.CollatralDtooInLoanToEnity(createLoanDto );
        collatral.setLoans(loans);

        collatralRepository.save(collatral);


    }
}

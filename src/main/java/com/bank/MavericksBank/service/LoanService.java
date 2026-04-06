package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.enums.LoanType;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.LoanMapper;
import com.bank.MavericksBank.model.*;
import com.bank.MavericksBank.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final UsersService usersService;

    public void createLoan(@Valid CreateLoanDto createLoanDto,String username) {


        Users users = (Users) usersService.loadUserByUsername(username);


        Customers customers = customerService.getByUsername(users.getUsername());

        Loans loans = LoanMapper.LoanDtoToEntity(createLoanDto);

customers.setIncomeCertificate(createLoanDto.incomeCertificate());




           loans.setCustomers(customers);
//        // assigning other varibales
       loans.setLoanStatus(LoanStatus.PENDING);
//
//        loanRepository.save(loans);
//
//        // 3 save's are there
//        // 1 save income certifivate in the customer
        System.out.println(customers);
        customerService.saveIncomeCertificate(customers);
//        // 2 save the loan type and loan amount in the loan

        System.out.println(loans);
        loanRepository.save(loans);
//        // 3 save collatrals
//






    }

    public void verifyLoan(@Valid VerifyTheLoanDto verifyTheLoanDto, String name) {

       Loans loans = loanRepository.findById(verifyTheLoanDto.loanId()).orElseThrow(() ->new ResourceNotFound("Loan id is invalid"));

       Users users = (Users) usersService.loadUserByUsername(name);

       if(!loans.getEmployees().getUsers().getUsername().equals(users.getUsername()))
           throw new AccountRemarksException("This account is no hold by this employee");


        Loans loans1 = LoanMapper.VerifyLoanDtoToEntity(loans,verifyTheLoanDto);


        loanRepository.save(loans1);


    }


    public List<GetLoanForEmployeeDto> getAllLoan() {
       // Pageable pageable = PageRequest.of(page, size);

        //List<GetLoanForEmployeeDto> loanDtoToEmployee = loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING);

      //  List<GetLoanForEmployeeDto> loanDtoToEmployee =loansPending.stream().map(LoanMapper:: EntityToDto).toList();
       return  loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING);
    }

    public Loans getById(long l) {
        return loanRepository.findById(l).orElseThrow(()->new ResourceNotFound("LoanId is invalid"));
    }

    public void AssigningEmpToLoan(AssignEmpsToLoanDto assignEmpsToLoanDto) {
        Loans loan = loanRepository.findById(assignEmpsToLoanDto.loanId()).orElseThrow(()->new ResourceNotFound("Account id is invalid"));

        Employees employees = employeeService.getById(assignEmpsToLoanDto.loanVerifier());
        Employees employees1 = employeeService.getById(assignEmpsToLoanDto.assestVerifier());
        Employees employees2 = employeeService.getById(assignEmpsToLoanDto.finaincialAnalyst());


        loan.setAssestVerifierId(assignEmpsToLoanDto.assestVerifier());
        loan.setFinancialAnalystId(assignEmpsToLoanDto.finaincialAnalyst());
        loan.setEmployees(employees);

        loanRepository.save(loan);
    }


    public List<LoanCustomerDto> getAllDetails(String name) {

        List<LoanCustomerDto>  l = loanRepository.getByUserDetails(name);
       List<LoanCustomerDto> fina  =  l.stream().distinct().toList();
        return fina;
    }

    public List<LoanResponseDto> getAllLoanDetails(String name) {

        return loanRepository.getAllLoanDetails(name);

    }

    public void verifyAndGiveRiskRates(RiskRateAndCollatralDto riskRateAndCollatralDto, String name) {

        Loans loans = getById(riskRateAndCollatralDto.loanId());
        Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(loans);
        System.out.println(name);

        loans.setRiskRating(riskRateAndCollatralDto.riskRating());

        loanRepository.save(loans);




    }

    public void loanConfirmation(LoanConfirmationDto loanConfirmationDto, String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        Loans loan = loanRepository.findById(loanConfirmationDto.loanId()).orElseThrow(()-> new AccountRemarksException("Account invalid"));


        loan.setCustomerLoanDecision(loanConfirmationDto.loanConfirmation());

        loanRepository.save(loan);

    }
}

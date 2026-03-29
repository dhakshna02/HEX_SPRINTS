package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.CreateLoanDto;
import com.bank.MavericksBank.dto.GetLoanForEmployeeDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.LoanMapper;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public void createLoan(@Valid CreateLoanDto createLoanDto) {

        Customers customers = customerService.getById(createLoanDto.customerId());
        Loans loans = LoanMapper.LoanDtoToEntity(createLoanDto);
        loans.setCustomers(customers);
        // assigning other varibales
        loans.setLoanStatus(LoanStatus.PENDING);

        loanRepository.save(loans);



    }

    public void verifyLoan(@Valid VerifyTheLoanDto verifyTheLoanDto) {
        Employees employees = employeeService.getById(verifyTheLoanDto.employeeId());
       Loans loans = loanRepository.findById(verifyTheLoanDto.loanId()).orElseThrow(() ->new ResourceNotFound("Loan id is invalid"));

        Loans loans1 = LoanMapper.VerifyLoanDtoToEntity(loans,verifyTheLoanDto);

        loans1.setEmployees(employees);
        loanRepository.save(loans1);


    }


    public List<GetLoanForEmployeeDto> getAllLoan() {
       // Pageable pageable = PageRequest.of(page, size);

        //List<GetLoanForEmployeeDto> loanDtoToEmployee = loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING);

      //  List<GetLoanForEmployeeDto> loanDtoToEmployee =loansPending.stream().map(LoanMapper:: EntityToDto).toList();
       return  loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING);
    }
}

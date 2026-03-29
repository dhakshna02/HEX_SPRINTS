package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.AccountVerificationDto;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.mapper.AccountMapper;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.repository.AccountsRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AccountService {
    private final CustomerService customerService;
    private final AccountsRepository accountsRepository;
    private final EmployeeService employeeService;



    public void createAccount(@Valid AccountDto accountDto) {

        // check the customer id and get the customer details
        Customers customers = customerService.getById(accountDto.customerId());

        Accounts accounts = new Accounts();
        // Assigning all the values to accounts

        accounts.setAccountType(accountDto.accountType());
        accounts.setBalance(BigDecimal.ZERO);
        accounts.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        accounts.setAccountStatus(AccountStatus.INACTIVE);
        accounts.setCustomers(customers);

        accountsRepository.save(accounts);


    }

    public void verifyingByEmployee(AccountVerificationDto accountVerificationDto) {
        // get the account
        Accounts account = accountsRepository.findById(accountVerificationDto.accountid()).orElseThrow(()-> new RuntimeException("Account id is invalid "));

        // get the employeee
        Employees employee = employeeService.getById(accountVerificationDto.empid());

        // check the approval
        if(accountVerificationDto.accountApprovedStatus() == AccountOpeningStatus.APPROVED){

            account.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);
            account.setAccountStatus(AccountStatus.ACTIVE);
        }
        if (accountVerificationDto.accountApprovedStatus() == AccountOpeningStatus.REJECTED){
            account.setAccountOpeningStatus(AccountOpeningStatus.REJECTED);
        }

        account.setAccountVerifiedDate(LocalDate.now());
        account.setEmployees(employee);
        accountsRepository.save(account);
    }


}

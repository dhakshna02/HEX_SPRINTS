package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.AccountMapper;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.AccountsRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final CustomerService customerService;
    private final AccountsRepository accountsRepository;
    private final EmployeeService employeeService;
    private final UsersService usersService;


    // creation of accounts
    public void createAccount(@Valid AccountDto accountDto, String name) {

       // user username from the token then use to get the customer details
        Users user = (Users) usersService.loadUserByUsername(name);

        // get the customer based on the user name
        Customers customers = customerService.getByUsername(user.getUsername());

        // mapping to the account
        Accounts accounts = AccountMapper.accountDtoToEntity(accountDto);

        // now add all the details that customer gave while creating will only be stored in the customer table and there it save other details
        customerService.saveOtherNecessaryDetails(customers,accountDto);



        // Assigning all the values to accounts
        accounts.setAccountType(accountDto.accountType());
        accounts.setBalance(BigDecimal.ZERO);
        accounts.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        accounts.setAccountStatus(AccountStatus.INACTIVE);
        accounts.setCustomers(customers);


        accountsRepository.save(accounts);


    }

    // getting all the unverfied accounts
    public List<GettingAllUnfiredAccountsWithCustomerDetails> getAllUnverifiedAccounts(String name) {

        return  accountsRepository.getAllUnverfiedAccounts(name,AccountOpeningStatus.PENDING);
    }


    // get the account details breifly
    public AccountInfoDto getAccountDetaiks(long id, String name) {


        log.atLevel(Level.WARN).log("Method called getAccountDetaiks");

        // getting the user name
        Users users =(Users) usersService.loadUserByUsername(name);

        Accounts account =getById(id);
        // check weather the given acct is the owner of the token



        // check weather the account is hold by this user
        System.out.println(account.toString());
        System.out.println(users.toString());



        if(users.getRole().equals(Role.CUSTOMER)){
            if(account.getCustomers().getUsers().getUsername() != users.getUsername())
                throw new AccountRemarksException("Account is not hold by this user");

        }
        if(users.getRole().equals(Role.EMPLOYEE)) {
            if (!account.getEmployees().getUsers().getUsername().equals(users.getUsername()) )
                throw new AccountRemarksException("Account is not hold by The employee user");
        }
        AccountInfoDto accountInfoDto = AccountMapper.AccountToDto(account);
        return accountInfoDto ;
    }


    public void AssigningEmpToAcct(long aid, long eid) {

        Accounts accounts = accountsRepository.findById(aid).orElseThrow(()->new ResourceNotFound("Account id is invalid"));

        Employees employees = employeeService.getById(eid);

        accounts.setEmployees(employees);

        accountsRepository.save(accounts);

    }

    public Accounts getById(long aid) {
        log.atLevel(Level.WARN).log("Method called Getbyid");
        return accountsRepository.findById(aid).orElseThrow(()-> new ResourceNotFound("Invalid Account id"));
    }

    public void reuploadDocs(long aid,AccountDto accountDto, String name) {

        Users user = (Users) usersService.loadUserByUsername(name);

        Customers customers = customerService.getByUsername(user.getUsername());


        Accounts accounts = getById(aid);

        if(!accounts.getCustomers().getUsers().getUsername().equals(name))
            throw new AccountRemarksException("Account is not hold by the customer");



        // now add all the details that customer gave while creating will only be stored in the customer table and there it save other details

        accounts.setAccountType(accountDto.accountType());
        customerService.saveOtherNecessaryDetails(customers,accountDto);
        accountsRepository.save(accounts);
    }

    // verifiying the accounts
    public void verifyingByEmployee(AccountVerificationDto accountVerificationDto) {
        // get the account
        Accounts account = getById(accountVerificationDto.accountid());

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

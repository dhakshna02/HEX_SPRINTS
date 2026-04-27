package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void createAccount(@Valid SignatureAndAccountTypeDto signatureAndAccountTypeDto, String name) {

       // user username from the token then use to get the customer details
        Users user = (Users) usersService.loadUserByUsername(name);




        // get the customer based on the user name
        Customers customers = customerService.getByUsername(user.getUsername());

        if(!customers.getDateOfBirth().isBefore(LocalDate.now().minusYears(18)))
            throw new AccountRemarksException("Your to young to create account  come back later");

        System.out.println(customers);

        // mapping to the account
        Accounts accounts = new Accounts();

        // now add all the details that customer gave while creating will only be stored in the customer table and there it save other details
        customerService.uploadSignature(signatureAndAccountTypeDto,name);



        // Assigning all the values to accounts
        accounts.setAccountType(AccountType.valueOf(signatureAndAccountTypeDto.accountType()));
        accounts.setBalance(BigDecimal.ZERO);
        accounts.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        accounts.setAccountStatus(AccountStatus.INACTIVE);
        accounts.setCustomers(customers);


        System.out.println(accounts);
        accountsRepository.save(accounts);


    }

    // getting all the unverfied accounts
    public GetAllAccountsWithUSerNameDetailsForManagerDto getAllUnverifiedAccounts(int page, int size,String name) {

        Pageable pageable = PageRequest.of(page,size);

        Page<Accounts> accounts = accountsRepository.getAllUnverfiedAccounts(name,AccountOpeningStatus.PENDING,pageable);

        List<GettingAllUnfiredAccountsWithCustomerDetails> accounts1 = accounts.toList().stream().map(AccountMapper::acctEntToDto).toList();

         return  new GetAllAccountsWithUSerNameDetailsForManagerDto(
                 accounts1,
                 accounts.getTotalPages(),
                 accounts.getTotalElements()
         );
    }


    // get the account details breifly
    public AccountInfoDtos getAccountDetaiks(long id, String name) {


        log.atLevel(Level.WARN).log("Method called getAccountDetaiks");

        // getting the user name
        Users users =(Users) usersService.loadUserByUsername(name);

        Accounts account = getById(id);
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
        AccountInfoDtos accountInfoDto = AccountMapper.AccountToDtoForWidget(account);
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
    public void verifyingByEmployee(AccountVerificationDto accountVerificationDto, String name) {
        // get the account
        Accounts account = getById(accountVerificationDto.accountid());

        System.out.println(account);
        // get the employeee
        Employees employee = employeeService.getByUsername(name);

        System.out.println(employee);
        // check the approval
        if(accountVerificationDto.accountApprovedStatus() == AccountOpeningStatus.APPROVED){

            account.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);
            account.setAccountStatus(AccountStatus.ACTIVE);
        }
        if (accountVerificationDto.accountApprovedStatus() == AccountOpeningStatus.REJECTED){
            account.setAccountOpeningStatus(AccountOpeningStatus.REJECTED);
        }

        System.out.println(account.getAccountStatus());

        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountVerifiedDate(LocalDate.now());
        account.setEmployees(employee);
        account.setAccountBranch("Chennai");
        account.setIFSC("IDFC032");
        System.out.println(account);
        accountsRepository.save(account);
    }



    public void depositupdateBalance( long id , BigDecimal value ) {

    accountsRepository.depositupdateBalance(id,value);

    }

    public void withDrawUpdateAccountBalance(long acctId, BigDecimal value) {
        accountsRepository.withDrawUpdateAccountBalance(acctId,value);
    }

    public void debitAmountForOtherBankTransfer(@Valid TransferDto transferDto) {

        accountsRepository.debitAmountForOtherBankTransfer(transferDto.sourceAccount(),transferDto.amount());
    }

    public GetAllUnfiredAccoutsDto getAllunverifiedAccount(int page, int size) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Accounts> accounts = accountsRepository.getAllUnverfiedAccountsWithNoVerifierId(pageable);

        List<GettingAllUnfiredAccountsWithCustomerDetails> account = accounts.toList().stream()
                .map(AccountMapper::AccountToAccOpeningDto).toList();

        return new GetAllUnfiredAccoutsDto(
                account,
                accounts.getTotalPages(),
                accounts.getTotalElements()
        );
    }


    public BigDecimal getAccountsByUserName(String name) {
        return  accountsRepository.getAccountsByUserName(name);
    }

    public SavingWidgetDto savingsWidget(String name) {


        List<Accounts> accounts = accountsRepository.getSavingsWidget(name, AccountType.SAVINGS);

        BigDecimal balance = BigDecimal.ZERO;

        for(Accounts a : accounts){

            balance =balance.add(a.getBalance());
        }


        return new SavingWidgetDto(
                balance,
                accounts.size()
        );
    }

    public List<Accounts> getAllAccountsByUserName(String name) {

        return accountsRepository.getAllAccountsByUserName(name);
    }

    public List<AccountInfoDtos> getByUserName(String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        List<Accounts> accounts = accountsRepository.getAllAccountsByUserName(name);

        System.out.println(accounts);

        return  accounts.stream().map(AccountMapper::AccountToDtoForWidget).toList();

    }

    public List<AccountsForDepositAndWithDrawDto> getAllAccountsByUsername(String name) {


        List<Accounts> accounts= getAllAccountsByUserName(name);

        return  accounts.stream().filter(a-> a.getAccountStatus().equals(AccountStatus.ACTIVE)).map(AccountMapper::AccountToDtoForDepositWidget).toList();





    }

    public void closeAccount(long id, AccountStatus status, String name) {

        Accounts accounts = accountsRepository.findById(id).orElseThrow(()->new ResourceNotFound("Invalid id "));

        Users users = (Users) usersService.loadUserByUsername(name);


        System.out.println("we"+accounts);
        if(!accounts.getCustomers().getUsers().getUsername().equals(users.getUsername()))
            throw new AccountRemarksException("Account is not hold by this user");

        if(! accounts.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new AccountRemarksException("Invalid Account status");

        if(!status.equals(AccountStatus.INACTIVE))
            throw new AccountRemarksException("Invalid transcation");


        accounts.setAccountStatus(status);

        accountsRepository.save(accounts);



    }


//    public List<AccountInfoDtos> searchAccounts(String keyword) {
//       List< Accounts> accounts=   accountsRepository.findByNameContainingIgnoreCase(keyword);
//
//      return accounts.stream().map(AccountMapper::AccountToDtoForWidget).toList();
//    }
}

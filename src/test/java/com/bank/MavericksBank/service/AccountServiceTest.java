package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.AccountMapper;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.AccountsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private UsersService usersService;

//    @Test
//    public void  getAllUnverifiedAccountsTest(){
//
//
//
//        // first create a accounts and other obj then change to the dto
//
//        Accounts accounts = new Accounts();
//        Customers customers = new Customers();
//        customers.setName("Dhaksss");
//
//        accounts.setId(12L);
//        accounts.setAccountType(AccountType.SAVINGS);
//        accounts.setBalance(BigDecimal.valueOf(34));
//        accounts.setOpenDate(LocalDate.now());
//        accounts.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);
//        accounts.setCustomers(customers);
//
//        Accounts accounts1 = new Accounts();
//        Customers customers1 = new Customers();
//        customers1.setName("Dhaksss");
//
//        accounts1.setId(12L);
//        accounts1.setAccountType(AccountType.SAVINGS);
//        accounts1.setBalance(BigDecimal.valueOf(34));
//        accounts1.setOpenDate(LocalDate.now());
//        accounts1.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
//        accounts1.setCustomers(customers);
//
//
//        // changing to dto
//
//        GettingAllUnfiredAccountsWithCustomerDetails unverifiedAcct = new GettingAllUnfiredAccountsWithCustomerDetails(
//                accounts.getId(),
//                accounts.getAccountType(),
//                accounts.getBalance(),
//                accounts.getOpenDate(),
//                accounts.getAccountOpeningStatus(),
//                accounts.getCustomers().getName()
//        );
//
//        GettingAllUnfiredAccountsWithCustomerDetails unverifiedAcct1 = new GettingAllUnfiredAccountsWithCustomerDetails(
//                accounts1.getId(),
//                accounts1.getAccountType(),
//                accounts1.getBalance(),
//                accounts1.getOpenDate(),
//                accounts1.getAccountOpeningStatus(),
//                accounts1.getCustomers().getName()
//        );
//
//
//        List<GettingAllUnfiredAccountsWithCustomerDetails> lis = List.of(unverifiedAcct,unverifiedAcct1);
//
//        // callling the repo
//        Mockito.when(accountsRepository.getAllUnverfiedAccounts(AccountOpeningStatus.PENDING))
//                .thenReturn(lis);
//        // calling the service
//       // Assertions.assertEquals(lis,accountService.getAllUnverifiedAccounts());
//
//        Mockito.verify(accountsRepository, times(1)).getAllUnverfiedAccounts(AccountOpeningStatus.PENDING);
//
//
//
//    }
//
//    @Test
//    public void getAccountDetaiksTest(){
//
//        Accounts accounts = new Accounts();
//        Customers customers = new Customers();
//        Employees employees = new Employees();
//
//
//        accounts.setId(12L);
//        customers.setName("Dhalsh");
//
//        accounts.setCustomers(customers);
//
//        accounts.setAccountType(AccountType.SAVINGS);
//        accounts.setBalance(BigDecimal.valueOf(45));
//        accounts.setAccountStatus(AccountStatus.ACTIVE);
//        accounts.setOpenDate(LocalDate.now());
//
//        employees.setId(3L);
//        employees.setName("Murgan");
//        accounts.setEmployees(employees);
//
//
//        AccountInfoDto accountInfoDto = new AccountInfoDto(
//
//                accounts.getId(),
//                accounts.getCustomers().getName(),
//                accounts.getAccountType(),
//                accounts.getBalance(),
//                accounts.getAccountStatus(),
//                accounts.getOpenDate(),
//                accounts.getEmployees().getId(),
//                accounts.getEmployees().getName()
//        );
//
//
//
//        Mockito.when(accountsRepository.findById(12L)).thenReturn(Optional.of(accounts));
//
//        Assertions.assertEquals(accountInfoDto,accountService.getAccountDetaiks(12, principal.getName()));
//
//        Mockito.verify(accountsRepository , times(1)).findById(12L);
//
//    }


    @Test
    public void getAllUnverifiedAccountsTest(){

        Accounts account1 = new Accounts();
        Customers customer1 = new Customers();
        customer1.setName("Dhakshna");

        account1.setId(1L);
        account1.setAccountType(AccountType.SAVINGS);
        account1.setBalance(BigDecimal.valueOf(100));
        account1.setOpenDate(LocalDate.now());
        account1.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        account1.setCustomers(customer1);

        Accounts account2 = new Accounts();
        Customers customer2 = new Customers();
        customer2.setName("Dhakshna");

        account2.setId(2L);
        account2.setAccountType(AccountType.CURRENT);
        account2.setBalance(BigDecimal.valueOf(200));
        account2.setOpenDate(LocalDate.now());
        account2.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        account2.setCustomers(customer2);

        Page<Accounts> page = new PageImpl<>(List.of(account1,account2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(accountsRepository.getAllUnverfiedAccounts("Dhakshna",AccountOpeningStatus.PENDING,pageable))
                .thenReturn(page);

        GetAllAccountsWithUSerNameDetailsForManagerDto result =
                accountService.getAllUnverifiedAccounts(0,2,"Dhakshna");

        Assertions.assertEquals(2,result.accounts().size());


        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAllUnverfiedAccounts("Dhakshna",AccountOpeningStatus.PENDING,pageable);

    }


    @Test
    public void getAccountDetaiksTest(){

        Accounts account = new Accounts();
        Customers customer = new Customers();
        Employees employee = new Employees();
        Users user = new Users();

        user.setUserName("Dhakshna");
        user.setRole(Role.CUSTOMER);

        customer.setName("Dhakshna");
        customer.setUsers(user);

        employee.setUsers(user);

        account.setId(12L);
        account.setCustomers(customer);
        account.setEmployees(employee);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(500));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setOpenDate(LocalDate.now());
        account.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);

        AccountInfoDtos expected = new AccountInfoDtos(
                account.getId(),
                account.getCustomers().getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus(),
                account.getOpenDate(),
                account.getAccountBranch(),
                account.getIFSC(),
                account.getAccountOpeningStatus().toString()
        );

        Mockito.when(usersService.loadUserByUsername("Dhakshna")).thenReturn(user);
        Mockito.when(accountsRepository.findById(12L)).thenReturn(Optional.of(account));

        Assertions.assertEquals(expected,
                accountService.getAccountDetaiks(12L,"Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1)).findById(12L);

    }


    @Test
    public void getByIdTestWhenExists(){

        Accounts account = new Accounts();

        account.setId(12L);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(500));
        account.setOpenDate(LocalDate.now());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);

        Mockito.when(accountsRepository.findById(12L))
                .thenReturn(Optional.of(account));

        Assertions.assertEquals(account,accountService.getById(12L));

        Mockito.verify(accountsRepository,Mockito.times(1)).findById(12L);

    }

    @Test
    public void getByIdWhenNotExits(){

        Mockito.when(accountsRepository.findById(12L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFound.class , ()->{
            accountService.getById(12L);}
        );

        Assertions.assertEquals("Invalid Account id",e.getMessage());

        Mockito.verify(accountsRepository,Mockito.times(1)).findById(12L);
    }

    @Test
    public void getAllunverifiedAccountTest(){

        Accounts account1 = new Accounts();
        Customers customer1 = new Customers();
        customer1.setName("Dhakshna");

        account1.setId(1L);
        account1.setAccountType(AccountType.SAVINGS);
        account1.setBalance(BigDecimal.valueOf(100));
        account1.setOpenDate(LocalDate.now());
        account1.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        account1.setCustomers(customer1);

        Accounts account2 = new Accounts();
        Customers customer2 = new Customers();
        customer2.setName("Dhakshna");

        account2.setId(2L);
        account2.setAccountType(AccountType.CURRENT);
        account2.setBalance(BigDecimal.valueOf(200));
        account2.setOpenDate(LocalDate.now());
        account2.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        account2.setCustomers(customer2);

        Page<Accounts> pageObj = new PageImpl<>(List.of(account1,account2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(accountsRepository.getAllUnverfiedAccountsWithNoVerifierId(pageable))
                .thenReturn(pageObj);

        GetAllUnfiredAccoutsDto result =
                accountService.getAllunverifiedAccount(0,2);

        Assertions.assertEquals(2,result.accounts().size());


        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAllUnverfiedAccountsWithNoVerifierId(pageable);

    }

    @Test
    public void getAccountsByUserNameTest(){

        BigDecimal amount = BigDecimal.valueOf(1500);

        Mockito.when(accountsRepository.getAccountsByUserName("Dhakshna"))
                .thenReturn(amount);

        Assertions.assertEquals(amount,
                accountService.getAccountsByUserName("Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAccountsByUserName("Dhakshna");

    }

    @Test
    public void savingsWidgetTest(){

        Accounts account1 = new Accounts();
        account1.setBalance(BigDecimal.valueOf(100));

        Accounts account2 = new Accounts();
        account2.setBalance(BigDecimal.valueOf(200));

        List<Accounts> list = List.of(account1,account2);

        SavingWidgetDto expected = new SavingWidgetDto(
                BigDecimal.valueOf(300),
                list.size()
        );

        Mockito.when(accountsRepository.getSavingsWidget("Dhakshna",AccountType.SAVINGS))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                accountService.savingsWidget("Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getSavingsWidget("Dhakshna",AccountType.SAVINGS);

    }


    @Test
    public void getAllAccountsByUserNameTest(){

        Accounts account1 = new Accounts();
        account1.setId(1L);
        account1.setAccountType(AccountType.SAVINGS);
        account1.setBalance(BigDecimal.valueOf(100));

        Accounts account2 = new Accounts();
        account2.setId(2L);
        account2.setAccountType(AccountType.CURRENT);
        account2.setBalance(BigDecimal.valueOf(200));

        List<Accounts> list = List.of(account1,account2);

        Mockito.when(accountsRepository.getAllAccountsByUserName("Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(list,
                accountService.getAllAccountsByUserName("Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAllAccountsByUserName("Dhakshna");

    }

    @Test
    public void getByUserNameTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");

        Accounts account1 = new Accounts();
        Customers customer1 = new Customers();
        customer1.setName("Dhakshna");
        customer1.setUsers(user);

        account1.setId(1L);
        account1.setCustomers(customer1);
        account1.setAccountType(AccountType.SAVINGS);
        account1.setBalance(BigDecimal.valueOf(100));
        account1.setAccountStatus(AccountStatus.ACTIVE);
        account1.setOpenDate(LocalDate.now());
        account1.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);

        Accounts account2 = new Accounts();
        Customers customer2 = new Customers();
        customer2.setName("Dhakshna");
        customer2.setUsers(user);

        account2.setId(2L);
        account2.setCustomers(customer2);
        account2.setAccountType(AccountType.CURRENT);
        account2.setBalance(BigDecimal.valueOf(200));
        account2.setAccountStatus(AccountStatus.ACTIVE);
        account2.setOpenDate(LocalDate.now());
        account2.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);

        List<Accounts> list = List.of(account1,account2);

        AccountInfoDtos dto1 = new AccountInfoDtos(
                account1.getId(),
                account1.getCustomers().getName(),
                account1.getAccountType(),
                account1.getBalance(),
                account1.getAccountStatus(),
                account1.getOpenDate(),
                account1.getAccountBranch(),
                account1.getIFSC(),
                account1.getAccountOpeningStatus().toString()
        );

        AccountInfoDtos dto2 = new AccountInfoDtos(
                account2.getId(),
                account2.getCustomers().getName(),
                account2.getAccountType(),
                account2.getBalance(),
                account2.getAccountStatus(),
                account2.getOpenDate(),
                account2.getAccountBranch(),
                account2.getIFSC(),
                account2.getAccountOpeningStatus().toString()
        );

        List<AccountInfoDtos> expected = List.of(dto1,dto2);

        Mockito.when(usersService.loadUserByUsername("Dhakshna")).thenReturn(user);
        Mockito.when(accountsRepository.getAllAccountsByUserName("Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                accountService.getByUserName("Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAllAccountsByUserName("Dhakshna");

    }

    @Test
    public void getAllAccountsByUsernameTest(){

        Accounts account1 = new Accounts();
        account1.setId(1L);
        account1.setAccountType(AccountType.SAVINGS);

        Accounts account2 = new Accounts();
        account2.setId(2L);
        account2.setAccountType(AccountType.CURRENT);

        List<Accounts> list = List.of(account1,account2);

        AccountsForDepositAndWithDrawDto dto1 =
                new AccountsForDepositAndWithDrawDto(
                        account1.getId(),
                        account1.getAccountType().toString()
                );

        AccountsForDepositAndWithDrawDto dto2 =
                new AccountsForDepositAndWithDrawDto(
                        account2.getId(),
                        account2.getAccountType().toString()
                );

        List<AccountsForDepositAndWithDrawDto> expected = List.of(dto1,dto2);

        Mockito.when(accountsRepository.getAllAccountsByUserName("Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                accountService.getAllAccountsByUsername("Dhakshna"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getAllAccountsByUserName("Dhakshna");

    }

}

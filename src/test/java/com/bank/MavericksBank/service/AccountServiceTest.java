package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AccountInfoDto;
import com.bank.MavericksBank.dto.GettingAllUnfiredAccountsWithCustomerDetails;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.repository.AccountsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
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

    @Test
    public void  getAllUnverifiedAccountsTest(){



        // first create a accounts and other obj then change to the dto

        Accounts accounts = new Accounts();
        Customers customers = new Customers();
        customers.setName("Dhaksss");

        accounts.setId(12L);
        accounts.setAccountType(AccountType.SAVINGS);
        accounts.setBalance(BigDecimal.valueOf(34));
        accounts.setOpenDate(LocalDate.now());
        accounts.setAccountOpeningStatus(AccountOpeningStatus.APPROVED);
        accounts.setCustomers(customers);

        Accounts accounts1 = new Accounts();
        Customers customers1 = new Customers();
        customers1.setName("Dhaksss");

        accounts1.setId(12L);
        accounts1.setAccountType(AccountType.SAVINGS);
        accounts1.setBalance(BigDecimal.valueOf(34));
        accounts1.setOpenDate(LocalDate.now());
        accounts1.setAccountOpeningStatus(AccountOpeningStatus.PENDING);
        accounts1.setCustomers(customers);


        // changing to dto

        GettingAllUnfiredAccountsWithCustomerDetails unverifiedAcct = new GettingAllUnfiredAccountsWithCustomerDetails(
                accounts.getId(),
                accounts.getAccountType(),
                accounts.getBalance(),
                accounts.getOpenDate(),
                accounts.getAccountOpeningStatus(),
                accounts.getCustomers().getName()
        );

        GettingAllUnfiredAccountsWithCustomerDetails unverifiedAcct1 = new GettingAllUnfiredAccountsWithCustomerDetails(
                accounts1.getId(),
                accounts1.getAccountType(),
                accounts1.getBalance(),
                accounts1.getOpenDate(),
                accounts1.getAccountOpeningStatus(),
                accounts1.getCustomers().getName()
        );


        List<GettingAllUnfiredAccountsWithCustomerDetails> lis = List.of(unverifiedAcct,unverifiedAcct1);

        // callling the repo
        Mockito.when(accountsRepository.getAllUnverfiedAccounts(AccountOpeningStatus.PENDING))
                .thenReturn(lis);
        // calling the service
        Assertions.assertEquals(lis,accountService.getAllUnverifiedAccounts());

        Mockito.verify(accountsRepository, times(1)).getAllUnverfiedAccounts(AccountOpeningStatus.PENDING);



    }

    @Test
    public void getAccountDetaiksTest(){

        Accounts accounts = new Accounts();
        Customers customers = new Customers();
        Employees employees = new Employees();


        accounts.setId(12L);
        customers.setName("Dhalsh");

        accounts.setCustomers(customers);

        accounts.setAccountType(AccountType.SAVINGS);
        accounts.setBalance(BigDecimal.valueOf(45));
        accounts.setAccountStatus(AccountStatus.ACTIVE);
        accounts.setOpenDate(LocalDate.now());

        employees.setId(3L);
        employees.setName("Murgan");
        accounts.setEmployees(employees);


        AccountInfoDto accountInfoDto = new AccountInfoDto(

                accounts.getId(),
                accounts.getCustomers().getName(),
                accounts.getAccountType(),
                accounts.getBalance(),
                accounts.getAccountStatus(),
                accounts.getOpenDate(),
                accounts.getEmployees().getId(),
                accounts.getEmployees().getName()
        );



        Mockito.when(accountsRepository.findById(12L)).thenReturn(Optional.of(accounts));

        Assertions.assertEquals(accountInfoDto,accountService.getAccountDetaiks(12));

        Mockito.verify(accountsRepository , times(1)).findById(12L);

    }
}

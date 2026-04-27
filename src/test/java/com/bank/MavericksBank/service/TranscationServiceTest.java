package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.GetAllTranscationDto;
import com.bank.MavericksBank.dto.InflowOutFlowDto;
import com.bank.MavericksBank.dto.getAllTranscationssDto;
import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.TranscationFlow;
import com.bank.MavericksBank.enums.TranscationType;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Transactions;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.TranscationRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TranscationServiceTest {
    @InjectMocks
    private TranscationService transcationService;
    @Mock
    private CustomerService customerService;
    @Mock
    private TranscationRepository transcationRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private UsersService usersService;

    @Test
    public void getAccountdetailsTest() {

        Users user = new Users();
        user.setUserName("Dhakshna");

        Customers customer = new Customers();
        customer.setId(1L);
        customer.setName("Dhakshna");
        customer.setUsers(user);

        Accounts account = new Accounts();
        account.setId(10L);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCustomers(customer);

        Mockito.when(accountService.getById(10L))
                .thenReturn(account);

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Assertions.assertEquals(account,
                transcationService.getAccountdetails(10L, "Dhakshna"));

        Mockito.verify(accountService, Mockito.times(1))
                .getById(10L);

    }


    @Test
    public void TranscationInOutFlowTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");

        Customers customer = new Customers();
        customer.setName("Dhakshna");

        Transactions t1 = new Transactions();
        t1.setAmount(BigDecimal.valueOf(100));
        t1.setTranscationflow(TranscationFlow.DEBIT);
        t1.setCreatedAt(LocalDateTime.now());

        Transactions t2 = new Transactions();
        t2.setAmount(BigDecimal.valueOf(200));
        t2.setTranscationflow(TranscationFlow.CREDIT);
        t2.setCreatedAt(LocalDateTime.now());

        Transactions t3 = new Transactions();
        t3.setAmount(BigDecimal.valueOf(50));
        t3.setTranscationflow(TranscationFlow.DEBIT);
        t3.setCreatedAt(LocalDate.now().minusMonths(1).atStartOfDay().plusDays(1));

        Transactions t4 = new Transactions();
        t4.setAmount(BigDecimal.valueOf(80));
        t4.setTranscationflow(TranscationFlow.CREDIT);
        t4.setCreatedAt(LocalDate.now().minusMonths(1).atStartOfDay().plusDays(2));

        List<Transactions> list = List.of(t1,t2,t3,t4);

        InflowOutFlowDto expected = new InflowOutFlowDto(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200),
                "Dhakshna",
                BigDecimal.valueOf(80),
                BigDecimal.valueOf(50)
        );

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Mockito.when(accountService.getAccountsByUserName("Dhakshna"))
                .thenReturn(BigDecimal.valueOf(1000));

        Mockito.when(customerService.getByUsername("Dhakshna"))
                .thenReturn(customer);

        Mockito.when(transcationRepository.getAllTransc(
                Mockito.eq("Dhakshna"),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        )).thenReturn(list);



        Assertions.assertEquals(expected,transcationService.TranscationInOutFlow("Dhakshna"));

        Mockito.verify(transcationRepository,Mockito.times(1))
                .getAllTransc(Mockito.eq("Dhakshna"),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

    }

    @Test
    public void getAllTrascationTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");
        Accounts acc = new Accounts();
        acc.setId(100L);

        Transactions t1 = new Transactions();
        t1.setId(1L);
        t1.setCreatedAt(LocalDateTime.now());
        t1.setAmount(BigDecimal.valueOf(100));
        t1.setTranscationType(TranscationType.DEPOSIT);
        t1.setTranscationflow(TranscationFlow.CREDIT);   // ✅ FIX

        Transactions t2 = new Transactions();
        t2.setId(2L);
        t2.setCreatedAt(LocalDateTime.now());
        t2.setAmount(BigDecimal.valueOf(200));
        t2.setTranscationType(TranscationType.WITHDRAW);
        t2.setTranscationflow(TranscationFlow.DEBIT);    // ✅ FIX

        t1.setSourceAccount(acc);
        t2.setSourceAccount(acc);
        List<Transactions> list = List.of(t1,t2);

        Page<Transactions> page = new PageImpl<>(list);

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Mockito.when(transcationRepository.getAllTranscations(
                Mockito.eq("Dhakshna"),
                Mockito.eq(pageable),
                Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class)
        )).thenReturn(page);

        getAllTranscationssDto result =
                transcationService.getAllTrascation("Dhakshna",0,2,null,null);

        Assertions.assertEquals(2,result.transcations().size());

        Mockito.verify(transcationRepository,Mockito.times(1))
                .getAllTranscations(
                        Mockito.eq("Dhakshna"),
                        Mockito.eq(pageable),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)
                );

    }
}

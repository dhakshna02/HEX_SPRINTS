package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.MultiAccountBalanceDto;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    // check the id exists
    @Test
    public void getByIdTestWhenExists(){

        Customers customers = new Customers();
        customers.setId(13L);
        customers.setName("Dhakhsa");
        customers.setMobileNo("3435442");
        customers.setMailId("fdckjnfsd");
        customers.setAddress("chennai");
        customers.setPanNo("6789");
        customers.setAadharNo("23489");


        when(repository.findById(13L)).thenReturn(Optional.of(customers));

        Assertions.assertEquals(customers,service.getById(13));

        Mockito.verify(repository,Mockito.times(1)).findById(13L);

    }

    @Test
    public void getByIdWhenNotExistsTest(){

        when(repository.findById(13L)).thenReturn(Optional.empty());

      Exception e =   Assertions.assertThrows(ResourceNotFound.class,()->{
            service.getById(13);
        });

      Assertions.assertEquals("Customer id is invalid",e.getMessage());

    }

    // testing the getAllAccountBalance metod in the service

    @Test
    public void getAllAccountBalanceTest(){

        Customers customer = new Customers();
        customer.setId(12L);

        Accounts account1 = new Accounts();
        account1.setId(23L);
        account1.setBalance(BigDecimal.valueOf(45));

        Accounts account2 = new Accounts();
        account2.setId(34);
        account2.setBalance(BigDecimal.valueOf(65));


        MultiAccountBalanceDto dto1 = new MultiAccountBalanceDto(
                account1.getId(),
                account1.getBalance()
        );

        MultiAccountBalanceDto dto2 = new MultiAccountBalanceDto(
                account2.getId(),
                account2.getBalance()
        );

        List<MultiAccountBalanceDto> lis = List.of(dto1,dto2);


        Mockito.when(repository.findById(12L))
                .thenReturn(Optional.of(customer));

        Mockito.when(repository.getAllAccountBalace(12L)).thenReturn(lis);

        Assertions.assertEquals(lis,service.getAllAccountBalance(12L));


    }


}

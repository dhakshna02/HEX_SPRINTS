package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.CustomerDto;
import com.bank.MavericksBank.dto.CustomerSignUpDto;
import com.bank.MavericksBank.dto.MultiAccountBalanceDto;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class CustomerMapper {

    public  static Customers CustomerDtoToCustomer(CustomerDto customerDto){
              Customers customers = new Customers();
                           customers.setName( customerDto.name());
                           customers.setMobileNo( customerDto.mobNumber());
                           customers.setMailId(customerDto.mailId());
                           customers.setAddress(customerDto.address());
                           customers.setPanNo(customerDto.panNo());
                           customers.setAadharNo(customerDto.aadharNo());

              return customers;

    }

    public static MultiAccountBalanceDto CutomerToDto(Accounts accounts){
        return new MultiAccountBalanceDto(
                accounts.getId(),
                accounts.getBalance()
        );
    }

    public static Customers SignUpDtoToEntity(@Valid CustomerSignUpDto customerSignUpDto) {
        Customers customers = new Customers();

        customers.setName(customerSignUpDto.name());
        customers.setMobileNo(customerSignUpDto.mobNumber());
        customers.setMailId(customerSignUpDto.mailId());
        customers.setAddress(customerSignUpDto.address());
        customers.setPanNo(customerSignUpDto.panNo());
        customers.setAadharNo(customerSignUpDto.aadharNo());

        return customers;

    }

    public static CustomerDto CustomerToCustomerDto(Customers customers) {
        return new CustomerDto(
                customers.getName(),
                customers.getMobileNo(),
                customers.getMailId(),
                customers.getAddress(),
                customers.getPanNo(),
                customers.getAadharNo()
        );
    }
}

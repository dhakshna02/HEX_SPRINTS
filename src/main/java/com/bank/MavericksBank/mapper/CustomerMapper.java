package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.CustomerDto;
import com.bank.MavericksBank.dto.CustomerSignUpDto;
import com.bank.MavericksBank.dto.MultiAccountBalanceDto;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Remarks;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomerMapper {

    public  static Customers CustomerDtoToCustomer(CustomerDto customerDto){
              Customers customers = new Customers();
                           customers.setName( customerDto.name());
                           customers.setMobileNo( customerDto.mobNumber());
                           customers.setMailId(customerDto.mailId());
                           customers.setAddress(customerDto.address());


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
        customers.setDateOfBirth(customerSignUpDto.DOB());
        customers.setGender(customerSignUpDto.gender());
        customers.setOccupation(customerSignUpDto.occupation());
        customers.setAnnualIncome(customerSignUpDto.annualIncome());



        return customers;

    }

    public static CustomerDto CustomerToCustomerDto(Customers customers) {
        return new CustomerDto(
                customers.getName(),
                customers.getMobileNo(),
                customers.getMailId(),
                customers.getAddress());
    }

    public static Customers idProofInfoDtoToEntity(Customers customers, @Valid AccountDto accountDto) {

        customers.setIdentityProof(accountDto.identityProof());
        customers.setAddressProof(accountDto.addressProof());
        customers.setPanNo(accountDto.panNo());
        customers.setPhotograph(accountDto.photograph());
        customers.setSignature(accountDto.signature());

        return customers;
    }
}

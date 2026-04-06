package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.CustomerDto;
import com.bank.MavericksBank.dto.CustomerSignUpDto;
import com.bank.MavericksBank.dto.MultiAccountBalanceDto;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.CustomerMapper;
import com.bank.MavericksBank.mapper.UserMapper;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    // creating new customer
    public void saveCustomer(@Valid CustomerDto customerDto) {
        Customers customers = CustomerMapper.CustomerDtoToCustomer(customerDto);
        customerRepository.save(customers);
    }

    // get customers by id
    public Customers getById(long l) {
        return customerRepository.findById(l).orElseThrow(()-> new ResourceNotFound("Incalid Customer id"));
    }

    // gett all account balance of one customer
    public List<MultiAccountBalanceDto> getAllAccountBalance(long id) {
        Customers customers = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Invalid id given"));// need to change to getbyid beacuse it already exists why need to
        // use that findbyid

       List<MultiAccountBalanceDto> multiAccountBalanceDtos = customerRepository.getAllAccountBalace(id);

       return multiAccountBalanceDtos;


    }

    // customer signup
    public void SaveCustomerSignUp(@Valid CustomerSignUpDto customerSignUpDto) {
            Users users = UserMapper.SignupDtoToEntity(customerSignUpDto);
            Customers customers = CustomerMapper.SignUpDtoToEntity(customerSignUpDto);
            users.setRole(Role.CUSTOMER);
            users.setPassword(passwordEncoder.encode(customerSignUpDto.password()));
           Users users1=  usersService.save(users);
            customers.setUsers(users1);
            customerRepository.save(customers);

    }

    // get the customer details
    public CustomerDto getAllDetailsOfCustomer(long id) {

        Customers customers = getById(id);

        CustomerDto customerDto = CustomerMapper.CustomerToCustomerDto(customers);

        return customerDto;
    }

    public Customers getByUsername(String username) {

        return customerRepository.getByUsername(username);
    }

    public void saveOtherNecessaryDetails(Customers customers, @Valid AccountDto accountDto) {

         Customers customers1 = CustomerMapper.idProofInfoDtoToEntity(customers,accountDto);
        customerRepository.save(customers1);
    }

    public void saveIncomeCertificate(Customers customers) {

        System.out.println(customers);

        customerRepository.save(customers);
    }
}

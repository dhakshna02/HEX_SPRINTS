package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/save-customer")
   public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDto customerDto){
        customerService.saveCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   // get all the account balance of one customer
    @GetMapping("/account-balance/{customerid}")
    public List<MultiAccountBalanceDto> AccountBalce(@PathVariable(value = "customerid") long id){
        return customerService.getAllAccountBalance(id);

    }


    // signup for the customer with security
    @PostMapping("/signup")
    public ResponseEntity<?> customerSignUpDto(@Valid @RequestBody CustomerSignUpDto customerSignUpDto){
        customerService.SaveCustomerSignUp(customerSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // get customer info of customer for profile
    @GetMapping("/cutomer-details/{id}")
    public CustomerDto getAllDetailsOfCustomer(@PathVariable(value = "id") long id){
        return customerService.getAllDetailsOfCustomer(id);
    }



    @GetMapping("/name")
    public NameDto getName(Principal principal){
        return customerService.getName(principal.getName());
    }




}

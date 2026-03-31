package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.AccountInfoDto;
import com.bank.MavericksBank.dto.AccountVerificationDto;
import com.bank.MavericksBank.dto.GettingAllUnfiredAccountsWithCustomerDetails;
import com.bank.MavericksBank.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;


    // creating the Account just basic and set account status = inactive and
    // acct opeing status as pending intialy then after approvval it changes fully
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid  @RequestBody AccountDto accountDto){
        accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Initated and soon verified By Employee");
    }

    // get all the accounts that are not verified
    @GetMapping("/get-all-unverified-account")
    public List<GettingAllUnfiredAccountsWithCustomerDetails> getAllUnverifiedAccounts(){
        List<GettingAllUnfiredAccountsWithCustomerDetails> unverfiedAccounts= accountService.getAllUnverifiedAccounts();
        return unverfiedAccounts;

    }

    // verifiying the customer account opeining
    @PutMapping("/verification")
    public ResponseEntity<?> verifyingByEmployee(@RequestBody AccountVerificationDto accountVerificationDto){

        accountService.verifyingByEmployee(accountVerificationDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // getting the account details by the account id
    @GetMapping("/account-details/{id}")
    public AccountInfoDto getAccountDetaiks(@PathVariable(value = "id") long id){
        return accountService.getAccountDetaiks(id);
    }


}

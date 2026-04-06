package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // creating the Account just basic and set account status = inactive and
    // acct opeing status as pending intialy then after approvval it changes fully
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid  @RequestBody AccountDto accountDto,
                                           Principal principal){
        accountService.createAccount(accountDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Initated and soon verified By Employee");
    }


    // Assigning the account
    @PutMapping("/assign-emp-acct/admin/{aid}/{eid}")
    public ResponseEntity<?> AssigningEmpToAcct(@PathVariable(value = "aid") long aid ,
                                                @PathVariable(value = "eid") long eid){
        accountService.AssigningEmpToAcct(aid,eid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // get all the accounts that are not verified
    @GetMapping("/get-all-unverified-account-with-username")
    public List<GettingAllUnfiredAccountsWithCustomerDetails> getAllUnverifiedAccounts(Principal principal){
        List<GettingAllUnfiredAccountsWithCustomerDetails> unverfiedAccounts =
                accountService.getAllUnverifiedAccounts(principal.getName());
        return unverfiedAccounts;

    }


    // getting the account details by the account id i need to change this to customer specified
    @GetMapping("/account-details/{id}")
    public AccountInfoDto getAccountDetaiks(@PathVariable(value = "id") long id,
                                            Principal principal){
        return accountService.getAccountDetaiks(id,principal.getName());
    }



    // we have to allow the customer to reupload the docs
    @PutMapping("/reupload/{aid}")
    public ResponseEntity<?> reuploadDocs(@PathVariable(value = "aid")long aid,
                                          @RequestBody AccountDto accountDto,
                                          Principal principal){
        accountService.reuploadDocs(aid,accountDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // verifiying the customer account opeining
    @PutMapping("/verification")
    public ResponseEntity<?> verifyingByEmployee(@RequestBody AccountVerificationDto accountVerificationDto){

        accountService.verifyingByEmployee(accountVerificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

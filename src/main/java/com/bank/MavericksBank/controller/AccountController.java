package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.AccountVerificationDto;
import com.bank.MavericksBank.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    // verifiying the customer account opeining
    @PutMapping("/verification")
    public ResponseEntity<?> verifyingByEmployee(@RequestBody AccountVerificationDto accountVerificationDto){

        accountService.verifyingByEmployee(accountVerificationDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

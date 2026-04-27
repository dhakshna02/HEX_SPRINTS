package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.AccountStatus;
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
@CrossOrigin("http://localhost:5173")
public class AccountController {

    private final AccountService accountService;

    // creating the Account just basic and set account status = inactive and
    // acct opeing status as pending intialy then after approvval it changes fully
//    @PostMapping("/create")
//    public ResponseEntity<?> createAccount(@Valid  @RequestBody AccountDto accountDto,
//                                           Principal principal){
//        accountService.createAccount(accountDto,principal.getName());
//        return ResponseEntity.status(HttpStatus.CREATED).body("Account Initated and soon verified By Employee");
//    }


    // Assigning the account
    @PutMapping("/assign-emp-acct/admin/{aid}/{eid}")
    public ResponseEntity<?> AssigningEmpToAcct(@PathVariable(value = "aid") long aid ,
                                                @PathVariable(value = "eid") long eid){
        accountService.AssigningEmpToAcct(aid,eid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // get all the accounts that are not verified
    @GetMapping("/get-all-unverified-account-with-username")
    public GetAllAccountsWithUSerNameDetailsForManagerDto getAllUnverifiedAccounts(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page ,
            @RequestParam(value = "size",defaultValue = "1",required = false) int size,
            Principal principal){

        return   accountService.getAllUnverifiedAccounts(page,size,principal.getName());


    }


    @GetMapping("/get-all-unverified-account")
    public GetAllUnfiredAccoutsDto getAllunverifiedAccount(

                    @RequestParam(value = "page",defaultValue = "0",required = false) int page ,
                    @RequestParam(value = "size",defaultValue = "10",required = false) int size

    ){
        return accountService.getAllunverifiedAccount(page,size);
    }


    // getting the account details by the account id i need to change this to customer specified
    @GetMapping("/account-details/{id}")
    public AccountInfoDtos getAccountDetaiks(@PathVariable(value = "id") long id,
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
    public ResponseEntity<?> verifyingByEmployee(@RequestBody AccountVerificationDto accountVerificationDto , Principal principal){

        accountService.verifyingByEmployee(accountVerificationDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // saving  account widgets

    @GetMapping("/saving-balance")
    public SavingWidgetDto savingsWidget(Principal principal){
        return accountService.savingsWidget(principal.getName());
    }

    @GetMapping("/get-by-userName")
    public List<AccountInfoDtos> getByUserName(Principal principal){
        return accountService.getByUserName(principal.getName());
    }



    @GetMapping("/get-all-accounts")
    public List<AccountsForDepositAndWithDrawDto> getAllAccounts(Principal principal){
        return accountService.getAllAccountsByUsername(principal.getName());
    }


    @PutMapping("/close")
    public ResponseEntity<HttpStatus> closeAccount(@RequestParam(value = "id") long id ,
                                                    @RequestParam(value = "status" ) AccountStatus status ,
                                                    Principal principal){

         accountService.closeAccount(id,status,principal.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



//    @GetMapping("/search")
//    public List<AccountInfoDtos> searchAccounts(@RequestParam String keyword) {
//        return accountService
//                .searchAccounts(keyword);
//    }
//









}

package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.AccountPostRemarksDto;
import com.bank.MavericksBank.dto.LoanRemarksDto;
import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.service.RemarksService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/remarks")
@AllArgsConstructor
public class RemarksController {

    private final RemarksService remarksService;

    // now the employee and the customer has to give remarks so that inital stage issues will be resolved

    @PostMapping("/opening-remarks")
    public ResponseEntity<?> createTheRemarksFromCustomerAndEmployee(@RequestBody AccountPostRemarksDto postRemarksDto, Principal principal) {
        remarksService.createTheRemarksFromCustomerAndEmployee(postRemarksDto, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // viewing all the remarks


    @GetMapping("/viewing-remarks/{aid}")
    public List<ViewRemarksDto> viewingAllRemarks(Principal principal,
                                                  @PathVariable(value = "aid") long aid){
        return remarksService.viewingAllRemarks(aid,principal.getName());
    }


    // adding remarks for the loan
    @PostMapping("/remarks/loan")
    public ResponseEntity<?> saveRemarksOfLoan(@RequestBody LoanRemarksDto loanRemarksDto,
                                               Principal principal){

        remarksService.saveRemarksOfLoan(loanRemarksDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // viewing the loan

    @GetMapping("/viewing-remarks-loan/{lid}")
    public List<ViewRemarksDto> viewingAllRemarksForLoan(Principal principal,
                                                  @PathVariable(value = "lid") long lid){
        return remarksService.viewingAllRemarksForLoan(lid,principal.getName());
    }
}

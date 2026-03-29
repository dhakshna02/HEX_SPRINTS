package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.CreateLoanDto;
import com.bank.MavericksBank.dto.GetLoanForEmployeeDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.service.LoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;


    // Creating the loan
    @PostMapping("/create-loan")
    public ResponseEntity<?> createLoan(@Valid @RequestBody CreateLoanDto createLoanDto){
        loanService.createLoan(createLoanDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // verifying the loan

    @PutMapping("/verify-loan")
    public ResponseEntity<?> verifyLoan(@Valid @RequestBody VerifyTheLoanDto verifyTheLoanDto){

        loanService.verifyLoan(verifyTheLoanDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all-loan-pending")
    public List<GetLoanForEmployeeDto> getLoansOfCustomer(){

       List<GetLoanForEmployeeDto> getLoanForEmployeeDto = loanService.getAllLoan();
        return getLoanForEmployeeDto;
    }


}

package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.repository.CollatralRepository;
import com.bank.MavericksBank.service.LoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/loan")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;


    // Creating the loan
    @PostMapping("/create-loan")
    public ResponseEntity<?> createLoan(@Valid @RequestBody CreateLoanDto createLoanDto,
                                        Principal principal){
        loanService.createLoan(createLoanDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // Assigning the loan
    @PutMapping("/assign-loan")
    public ResponseEntity<?> AssigningEmpToLoan(@RequestBody AssignEmpsToLoanDto assignEmpsToLoanDto ){
        loanService.AssigningEmpToLoan(assignEmpsToLoanDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
// loan re submission is not done

    // verifying the loan


    @GetMapping("/get-all-loan-pending")
    public List<GetLoanForEmployeeDto> getLoansOfCustomer(){

       List<GetLoanForEmployeeDto> getLoanForEmployeeDto = loanService.getAllLoan();
        return getLoanForEmployeeDto;
    }




    // Getting all the customer detials for verification

    @GetMapping("/all-cust-details")
    public List<LoanCustomerDto> getAllDetails(Principal principal){
        return loanService.getAllDetails(principal.getName());
    }
    // getting all the loan details of the employee

    @GetMapping("/get-loans")
    public List<LoanResponseDto> getAllLoanDetails(Principal principal){
        return loanService.getAllLoanDetails(principal.getName());

    }


    // after this he verifies and give the risk rates
    @PutMapping("/verify-and-riskRates")
    public ResponseEntity<?> verifyAndGiveRiskRates(@RequestBody RiskRateAndCollatralDto riskRateAndCollatralDto,
                                                                     Principal principal){
        loanService.verifyAndGiveRiskRates(riskRateAndCollatralDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PutMapping("/verify-loan")
    public ResponseEntity<?> verifyLoan(@Valid @RequestBody VerifyTheLoanDto verifyTheLoanDto,
                                        Principal principal){

        loanService.verifyLoan(verifyTheLoanDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





    // customer accepts the loan
    @PutMapping("/loan-confirmation")
    public  ResponseEntity<?>  loanConfirmation(@RequestBody LoanConfirmationDto loanConfirmationDto,
                                                Principal principal){

        loanService.loanConfirmation(loanConfirmationDto,principal.getName());
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.service.TranscationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transcation")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class TranscationController {

    private final TranscationService transcationService;
    // deposit

            @PostMapping("/deposit")
            public ResponseEntity<HttpStatus> deposits(@Valid @RequestBody DepositDto depositDto , Principal principal ) {
                transcationService.deposit(depositDto, principal.getName());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }



            // withdraw


            @PostMapping("/withdraw")
            public ResponseEntity<HttpStatus> withdraw(@Valid @RequestBody WithdrawDto withdrawDto, Principal principal){

                transcationService.withdraw(withdrawDto,principal.getName());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }


            // transfer for other bank

            @PostMapping("/transfer/other-bank")
            public ResponseEntity<HttpStatus> transfer(@Valid @RequestBody TransferDto transferDto, Principal principal){
                transcationService.transferOtherBank(transferDto, principal.getName());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }


            // transfer inside the bank

            //transferInsideBank()

            @PostMapping("/transfer/inside-bank")
            public ResponseEntity<HttpStatus> transferInsideBank(@Valid @RequestBody TransferInsideDto transferInsideDto,
                                                                 Principal principal){

                transcationService.transferInsideBank(transferInsideDto,principal.getName());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }



            // transcation stat

            @GetMapping("/inflow-outflow")
            public InflowOutFlowDto TranscationInOutFlow(Principal principal){
               return transcationService.TranscationInOutFlow(principal.getName());
            }


            @GetMapping ("/get-all-Transction")
            public getAllTranscationssDto getAllTranscations(Principal principal ,
                                                                 @RequestParam(value = "page" ,defaultValue = "0",required = false) int page,
                                                                 @RequestParam(value = "size" ,defaultValue = "5",required = false) int size,
                                                             @RequestParam(value ="fromDate" , required = false) LocalDate fromDate,
                                                             @RequestParam(value ="toDate" , required = false) LocalDate toDate
                                                             ){
                return transcationService.getAllTrascation(principal.getName(),page,size, fromDate, toDate);
            }










}

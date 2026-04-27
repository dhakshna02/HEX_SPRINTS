package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.BenficaryAddDto;
import com.bank.MavericksBank.dto.BenificaryReDto;
import com.bank.MavericksBank.service.BenificaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/benificary")
@CrossOrigin("http://localhost:5173")
public class BenificaryController {

    private final BenificaryService benificaryService;


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addBenificary(@RequestBody BenficaryAddDto dto, Principal principal){

        benificaryService.addBenificary(dto , principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public BenificaryReDto  getBenificary(@RequestParam(value = "page",defaultValue = "0",required = false) int page,
                                          @RequestParam(value = "size",defaultValue = "5",required = false) int size, Principal principal){
        return benificaryService.getBenificary(page,size,principal.getName());
    }
}

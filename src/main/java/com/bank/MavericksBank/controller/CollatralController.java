package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.dto.CollatralValueDto;
import com.bank.MavericksBank.service.CollatralService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/collatral")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CollatralController {
    private final CollatralService collatralService;

    // giveing the collatrals

    @PostMapping("/addcollatral")
    public ResponseEntity<?> addCollatral(@RequestBody CollatralDto collatralDto, Principal principal){

        collatralService.addCollatral(collatralDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/collatrals/{lid}")
    public CollatralResponseDto getCollatrals(@PathVariable(value = "lid") long lid,
                                                    Principal principal){
        return collatralService.getCollatrals(lid,principal.getName());
    }

    @PutMapping("/collatral-value")
    public ResponseEntity<?> addCollatralValue(@RequestBody CollatralValueDto collatralValueDto,
                                               Principal principal){
        collatralService.addCollatralValue(collatralValueDto,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }





}

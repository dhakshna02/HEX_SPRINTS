package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.AdminReqDto;
import com.bank.MavericksBank.dto.AssignEmpsToLoanDto;
import com.bank.MavericksBank.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminReqDto adminReqDto){
        adminService.addAdmin(adminReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}

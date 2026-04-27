package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.AdminReqDto;
import com.bank.MavericksBank.dto.AssignEmpsToLoanDto;
import com.bank.MavericksBank.dto.EmpIdsDto;
import com.bank.MavericksBank.dto.ManagerDto;
import com.bank.MavericksBank.service.AdminService;
import com.bank.MavericksBank.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminReqDto adminReqDto){
        adminService.addAdmin(adminReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    // get all manager for account approval

    @GetMapping("/get-managers")
    public List<ManagerDto> getAllManager(){
        return employeeService.getAllManager();
    }


    @GetMapping("/get-employees")
    public EmpIdsDto getAllEmployess(){
        return employeeService.getAllEmployess();
    }


}

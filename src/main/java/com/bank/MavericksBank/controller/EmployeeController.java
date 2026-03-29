package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeSignUpDto employeeSignUpDto){
        employeeService.addEmployee(employeeSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Succesfully created empolyee");
    }

}

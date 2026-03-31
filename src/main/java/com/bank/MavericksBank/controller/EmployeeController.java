package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.EmployeeDto;
import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // get the employee details

    @GetMapping("/get-emp-details/{id}")
    public EmployeeDto getDetailsOfEmployee(@PathVariable(value = "id") long id ){
        return employeeService.getDetailsOfEmployee(id);
    }


}

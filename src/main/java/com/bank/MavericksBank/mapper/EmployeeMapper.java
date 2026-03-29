package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.model.Employees;
import lombok.AllArgsConstructor;


public class EmployeeMapper {

    public static Employees EmployeedtoToEmployee(EmployeeSignUpDto employeeSignUpDto){
        Employees employees = new Employees();

        employees.setName(employeeSignUpDto.name());
        employees.setEmail(employeeSignUpDto.email());
        employees.setMobNo(employeeSignUpDto.mobNo());
        employees.setDesignation(employeeSignUpDto.designation());
        return employees;
    }
}

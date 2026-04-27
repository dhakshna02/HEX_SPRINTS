package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.EmployeeDto;
import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.dto.ManagerDto;
import com.bank.MavericksBank.model.Employees;
import lombok.AllArgsConstructor;

import java.util.List;


public class EmployeeMapper {

    public static Employees EmployeedtoToEmployee(EmployeeSignUpDto employeeSignUpDto){
        Employees employees = new Employees();

        employees.setName(employeeSignUpDto.name());
        employees.setEmail(employeeSignUpDto.email());
        employees.setMobNo(employeeSignUpDto.mobNo());
        employees.setDesignation(employeeSignUpDto.designation());
        return employees;
    }

    public static EmployeeDto emptoDto(Employees employee) {
        return new EmployeeDto(
                employee.getName(),
                employee.getEmail(),
                employee.getMobNo(),
                employee.getDesignation()
        );
    }

    public static ManagerDto EmpToDto(Employees employees) {

        return new ManagerDto(
                employees.getId(),
                employees.getName()
        );

    }


}

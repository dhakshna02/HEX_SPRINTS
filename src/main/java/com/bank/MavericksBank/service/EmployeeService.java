package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.EmployeeDto;
import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.dto.VerifyTheLoanDto;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.EmployeeMapper;
import com.bank.MavericksBank.mapper.UserMapper;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;

    public Employees getById(long empid) {
        return employeeRepository.findById(empid).orElseThrow(()-> new ResourceNotFound(" Employee id is invalid"));
    }

    public void addEmployee(EmployeeSignUpDto employeeSignUpDto) {
        Employees employees = EmployeeMapper.EmployeedtoToEmployee(employeeSignUpDto);

        Users users = UserMapper.EmpSignupDtoToEntity(employeeSignUpDto);

        users.setPassword(passwordEncoder.encode(employeeSignUpDto.password()));
        users.setRole(Role.EMPLOYEE);
        Users users1 = usersService.save(users);

        employees.setUsers(users1);

        employeeRepository.save(employees);

    }

    // get employee details
    public EmployeeDto getDetailsOfEmployee(long id) {
        Employees employee = getById(id);

        return  EmployeeMapper.emptoDto(employee);
    }


}
// ipo empolyee the loan approve pana poraru so athu loan la varuma ila employee la varuma

package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.CustomerSignUpDto;
import com.bank.MavericksBank.dto.EmployeeSignUpDto;
import com.bank.MavericksBank.model.Users;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static Users SignupDtoToEntity(@Valid CustomerSignUpDto customerSignUpDto) {
    Users users = new Users();

    users.setUserName(customerSignUpDto.userName());

    return users;
    }

    public static Users EmpSignupDtoToEntity(EmployeeSignUpDto employeeSignUpDto) {
        Users users = new Users();
        users.setUserName(employeeSignUpDto.userName());

        return users;
    }
}

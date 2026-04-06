package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AdminReqDto;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addAdmin(AdminReqDto adminReqDto) {
        Users users = new Users();
        users.setUserName(adminReqDto.username());
        users.setPassword(passwordEncoder.encode(adminReqDto.password()));
        users.setRole(Role.ADMIN);
        userRepository.save(users);
    }
}

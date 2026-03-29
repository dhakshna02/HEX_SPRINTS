package com.bank.MavericksBank.service;

import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
// this implements the userDetailsService interface beacuse Authentication manager requires the userdetialservice
// but we have only userservice so we need to implent its class and convert our user name to simple au
public class UsersService implements UserDetailsService {
    private final UserRepository userRepository;
    public Users save(Users users) {
        System.out.println(users);
        return userRepository.save(users);
    }


    // getting the username by using the user name
    // see we have username in the argument then i neet to return in UserDetils for the finding the user name exits in the db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserNamer(username);
        return users;
    }
}

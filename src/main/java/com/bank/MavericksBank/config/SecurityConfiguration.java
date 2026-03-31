package com.bank.MavericksBank.config;

import com.bank.MavericksBank.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private final UsersService usersService;
    private final JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws  Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize)->authorize
                        // signup and login
                        .requestMatchers(HttpMethod.POST,"/api/customer/signup").permitAll()  // works good
                        .requestMatchers(HttpMethod.POST,"/api/employee/create").permitAll()// works good
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()// works good

                        // customer relate
                        .requestMatchers(HttpMethod.GET,"/api/customer/account-balance/{customerid}").hasAuthority("CUSTOMER")// works good
                        .requestMatchers(HttpMethod.GET,"/api/customer/cutomer-details/{id}").authenticated()// works good can be used for customer details

                        // account related
                        .requestMatchers(HttpMethod.POST,"/api/account/create").hasAuthority("CUSTOMER") // works good
                        .requestMatchers(HttpMethod.GET,"/api/account/get-all-unverified-account").hasAuthority("EMPLOYEE")//works good
                        .requestMatchers(HttpMethod.PUT,"/api/account/verification").hasAuthority("EMPLOYEE")// works good
                        .requestMatchers(HttpMethod.GET,"/api/account/account-details/{id}").authenticated()// works good

                        // employyee related
                        .requestMatchers(HttpMethod.GET,"/api/employee/get-emp-details/{id}").hasAuthority("EMPLOYEE")

                        // loan related
                        .requestMatchers(HttpMethod.POST,"/api/loan/create-loan").hasAuthority("CUSTOMER")// works good
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-all-loan-pending").hasAuthority("EMPLOYEE") // works good
                        .requestMatchers(HttpMethod.PUT,"/api/loan/verify-loan").hasAuthority("EMPLOYEE")// works good
                );
                     http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                    http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(
//                                                        UserDetailsService usersDetailService,
//                                                        PasswordEncoder passwordEncoder){
//
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(usersService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        return new ProviderManager(daoAuthenticationProvider);
//    }





}

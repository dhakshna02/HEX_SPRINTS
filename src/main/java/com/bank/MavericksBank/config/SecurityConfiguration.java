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
                        .requestMatchers(HttpMethod.POST,"/api/admin/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/auth/user-Details").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/customer/name").authenticated()

                        // customer relate
                        .requestMatchers(HttpMethod.GET,"/api/customer/account-balance/{customerid}").hasAuthority("CUSTOMER")// works good
                        .requestMatchers(HttpMethod.GET,"/api/customer/cutomer-details/{id}").authenticated()// works good can be used for customer details

                        // account related
                        .requestMatchers(HttpMethod.POST,"/api/account/create").hasAuthority("CUSTOMER") // works good
                        .requestMatchers(HttpMethod.GET,"/api/account/get-all-unverified-account").hasAnyAuthority("EMPLOYEE","ADMIN")//works good
                        .requestMatchers(HttpMethod.PUT,"/api/account/verification").hasAuthority("EMPLOYEE")// works good
                        .requestMatchers(HttpMethod.GET,"/api/account/account-details/{id}").authenticated()// works good
                        .requestMatchers(HttpMethod.PUT,"/api/account/assign-emp-acct/admin/{aid}/{eid}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/account/get-all-unverified-account-with-username").hasAnyAuthority("EMPLOYEE","CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/account/reupload/{aid}").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/account/saving-balance").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/account/get-by-userName").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/account/get-all-accounts").hasAuthority("CUSTOMER")


                        .requestMatchers(HttpMethod.PUT,"/api/account/close").hasAuthority("CUSTOMER")


                        // employyee related
                        .requestMatchers(HttpMethod.GET,"/api/employee/get-emp-details/{id}").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employee/designation").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employee/stats/manager").hasAuthority("ADMIN")

                        // loan related
                        .requestMatchers(HttpMethod.POST,"/api/loan/create-loan").hasAuthority("CUSTOMER")// works good
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-all-loan-pending").hasAnyAuthority("EMPLOYEE","ADMIN") // works good
                        .requestMatchers(HttpMethod.PUT,"/api/loan/verify-loan").hasAuthority("EMPLOYEE")// works good
                        .requestMatchers(HttpMethod.PUT,"/api/loan/assign-loan").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/loan/all-cust-details").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-loans").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/loan/verify-and-riskRates").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/loan/active-loan-amount").hasAnyAuthority("EMPLOYEE","CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-loans/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-all-by-fin").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-other-loan/{id}").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/loan/get-all-by-Assest").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/loan/all-loans").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/loan/decision/{id}").hasAuthority("EMPLOYEE")






                        // Remarks related
                        .requestMatchers(HttpMethod.POST,"/api/remarks/opening-remarks").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/remarks/viewing-remarks/{aid}").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/remarks/remarks/loan").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/remarks/viewing-remarks-loan/{lid}").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT,"/api/remarks/update-remarks/{status}/{id}").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                         .requestMatchers(HttpMethod.GET,"/api/remarks/viewing-loan-remakrs").hasAnyAuthority("CUSTOMER","EMPLOYEE")



                        // collatral
                        .requestMatchers(HttpMethod.POST,"/api/collatral/addcollatral").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/collatral/collatrals/{lid}").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT,"/api/collatral/collatral-value").hasAuthority("EMPLOYEE")

                        // ADMIN RELATED
                        .requestMatchers(HttpMethod.GET,"/api/admin/get-managers").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/get-employees").hasAuthority("ADMIN")

                        // transcation
                        .requestMatchers(HttpMethod.POST,"/api/transcation/deposit").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/transcation/withdraw").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/transcation/transfer/other-bank").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/transcation/inflow-outflow").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/transcation/get-all-Transction").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/transcation/transfer/inside-bank").hasAuthority("CUSTOMER")




                        // document realted

                        .requestMatchers(HttpMethod.POST,"/api/document/upload").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/document/upload-identity-proof").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/document/upload-address-proof").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/document/upload-pan").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/document/upload-photo").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/document/initate-account-signature-upload").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/document/signature-upload").hasAuthority("CUSTOMER")

                        // benificiers
                        .requestMatchers(HttpMethod.POST,"/api/benificary/add").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/benificary/get").hasAuthority("CUSTOMER")

                        .anyRequest().permitAll()
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

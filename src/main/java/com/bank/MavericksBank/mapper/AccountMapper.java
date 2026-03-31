package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.AccountDto;
import com.bank.MavericksBank.dto.AccountInfoDto;
import com.bank.MavericksBank.dto.AccountVerificationDto;
import com.bank.MavericksBank.model.Accounts;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class AccountMapper {


    public static AccountInfoDto AccountToDto(Accounts account) {

        return new AccountInfoDto(
                account.getId(),
                account.getCustomers().getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus(),
                account.getOpenDate(),
                account.getEmployees().getId(),
                account.getEmployees().getName()
        );
    }
}

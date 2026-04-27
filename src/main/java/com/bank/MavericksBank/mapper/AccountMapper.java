package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.model.Accounts;
import jakarta.validation.Valid;
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

    public static Accounts accountDtoToEntity(@Valid AccountDto accountDto) {


        Accounts accounts = new Accounts();

        accounts.setAccountType(accountDto.accountType());

        return accounts;
    }


    public static GettingAllUnfiredAccountsWithCustomerDetails AccountToAccOpeningDto(Accounts accounts) {

        return  new GettingAllUnfiredAccountsWithCustomerDetails(
                accounts.getId(),
                accounts.getAccountType(),
                accounts.getBalance(),
                accounts.getOpenDate(),
                accounts.getAccountOpeningStatus(),
                accounts.getCustomers().getName(),
                accounts.getCustomers().getIdentityProof(),
                accounts.getCustomers().getAddressProof(),
                accounts.getCustomers().getPanNo()
        );
    }

    public static AccountInfoDtos AccountToDtoForWidget(Accounts account) {
        return new AccountInfoDtos(
                account.getId(),
                account.getCustomers().getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus(),
                account.getOpenDate(),
                account.getAccountBranch(),
                account.getIFSC(),
                account.getAccountOpeningStatus().toString()

        );
    }

    public static AccountsForDepositAndWithDrawDto AccountToDtoForDepositWidget(Accounts accounts) {

        return new AccountsForDepositAndWithDrawDto(
                accounts.getId(),
                accounts.getAccountType().toString()
        );
    }

    public static GettingAllUnfiredAccountsWithCustomerDetails acctEntToDto(Accounts accounts) {
        return  new GettingAllUnfiredAccountsWithCustomerDetails(
                accounts.getId(),
                accounts.getAccountType(),
                accounts.getBalance(),
                accounts.getOpenDate(),
                accounts.getAccountOpeningStatus(),
                accounts.getCustomers().getName(),
                accounts.getCustomers().getIdentityProof(),
                accounts.getCustomers().getAddressProof(),
                accounts.getCustomers().getPanNo()
        );
    }
}

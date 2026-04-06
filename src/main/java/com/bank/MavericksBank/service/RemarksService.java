package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AccountPostRemarksDto;
import com.bank.MavericksBank.dto.LoanRemarksDto;
import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.enums.Role;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.model.Remarks;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.RemarksReposiotry;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RemarksService {
    private final RemarksReposiotry remarksReposiotry;
    private final UsersService usersService;
    private final AccountService accountService;
    private final LoanService loanService;

    // if the account is under verification and also checks the ownership of accont and the remarks
    public void createTheRemarksFromCustomerAndEmployee(AccountPostRemarksDto postRemarksDto, String name) {

        // get the user details weather the user is valid
        Users user = (Users) usersService.loadUserByUsername(name);

        // get the account details and check the valid in db
        Accounts accounts = accountService.getById(postRemarksDto.accountId());





        // check that account is hold by the proper user
        // accounts->customers->users->username should be equal to the username from the token

            if(!accounts.getAccountOpeningStatus().equals(AccountOpeningStatus.PENDING) )
                throw new AccountRemarksException("Account is already verified and remarks cannot be added");

            if (user.getRole() == Role.CUSTOMER) {
                if (!accounts.getCustomers().getUsers().getUsername().equals(user.getUsername()))
                    throw new AccountRemarksException("This customer is not owner of this account");

            }

//            if (user.getRole() == Role.EMPLOYEE) {
//                if (!accounts.getEmployees().getUsers().getUsername().equals(user.getUsername())) {
//                    throw new AccountRemarksException("This account is not managed by this employee");
//                }
//
//                if (accounts.getEmployees() == null) {
//                    throw new AccountRemarksException("This account is not managed by this employee");
//                }
//            }

            Remarks remarks = new Remarks();
            remarks.setRole(user.getRole());
            remarks.setRemarks(postRemarksDto.remarks());
            remarks.setAccounts(accounts);


            remarksReposiotry.save(remarks);

        }

    public List<ViewRemarksDto> viewingAllRemarks(long aid, String name) {

        // verify the user holds the account
      //  Users users = (Users) usersService.loadUserByUsername(name);

        Accounts accounts = accountService.getById(aid);

        if(accounts.getCustomers().getUsers().getRole().equals(Role.CUSTOMER)) {
            if (!accounts.getCustomers().getUsers().getUsername().equals(name))
                throw new AccountRemarksException("Account Owner mismatch");

        }
        if(accounts.getCustomers().getUsers().getRole().equals(Role.EMPLOYEE)) {

            if (!accounts.getEmployees().getUsers().getUsername().equals(name))
                throw new AccountRemarksException("Emplyee doesnt manage this account");

        }
         return  remarksReposiotry.viewingAllRemarks(aid);

    }




    public void saveRemarksOfLoan(LoanRemarksDto loanRemarksDto, String name) {

        Users user = (Users) usersService.loadUserByUsername(name);

      //   getting the loan details

            Loans loans = loanService.getById(loanRemarksDto.loanId());

        if(!loans.getLoanStatus().equals(LoanStatus.PENDING) )
            throw new AccountRemarksException("Account is already verified and remarks cannot be added");



        if (user.getRole() == Role.CUSTOMER) {
            if (!loans.getCustomers().getUsers().getUsername().equals(user.getUsername()))
                throw new AccountRemarksException("This customer is not owner of this account");

        }


        System.out.println(loans.toString());

        if (user.getRole().equals(Role.EMPLOYEE) ){

            if (!loans.getEmployees().getUsers().getUsername().equals(user.getUsername())) {
                System.out.println(user.getRole());
                throw new AccountRemarksException("This account is not managed by this employee");

            }

            if (loans.getEmployees() == null) {
                throw new AccountRemarksException("This account is not managed by this employee");
            }
        }
        System.out.println(user.getRole());
        Remarks remarks = new Remarks();
        remarks.setRole(user.getRole());
        remarks.setRemarks(loanRemarksDto.remarks());
        remarks.setLoans(loans);


        remarksReposiotry.save(remarks);


    }





    public List<ViewRemarksDto> viewingAllRemarksForLoan(long lid, String name) {

        // verify the user holds the account
        //  Users users = (Users) usersService.loadUserByUsername(name);

        Loans loan = loanService.getById(lid);

        if(loan.getCustomers().getUsers().getRole().equals(Role.CUSTOMER)) {
            if (!loan.getCustomers().getUsers().getUsername().equals(name))
                throw new AccountRemarksException("Account Owner mismatch");

        }
        if(loan.getCustomers().getUsers().getRole().equals(Role.EMPLOYEE)) {

            if (!loan.getEmployees().getUsers().getUsername().equals(name))
                throw new AccountRemarksException("Emplyee doesnt manage this account");

        }
        return  remarksReposiotry.viewingAllRemarksForLoan(lid);

    }
}

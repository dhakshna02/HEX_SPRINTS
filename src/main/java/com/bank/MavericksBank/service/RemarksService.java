package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.AccountPostRemarksDto;
import com.bank.MavericksBank.dto.LoanRemarksDto;
import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.enums.*;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.mapper.RemarksMapper;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.model.Remarks;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.LoanRepository;
import com.bank.MavericksBank.repository.RemarksReposiotry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RemarksService {
    private final RemarksReposiotry remarksReposiotry;
    private final UsersService usersService;
    private final AccountService accountService;
    private final LoanService loanService;
    private final LoanRepository loanRepository;

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
            remarks.setRemarkStatus(RemarkStatus.ACTIVE);


            remarksReposiotry.save(remarks);

        }

    public List<ViewRemarksDto> viewingAllRemarks( String name) {

        // verify the user holds the account
       Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(users);
       List<Remarks> remarks = remarksReposiotry.viewingAllRemarks(users.getUsername(),RemarkStatus.ACTIVE);

        System.out.println(remarks);
       return remarks.stream().map(RemarksMapper:: entToDto).toList();


    }




    public void saveRemarksOfLoan(LoanRemarksDto loanRemarksDto, String name) {

        Users user = (Users) usersService.loadUserByUsername(name);

      //   getting the loan details

            Loans loans = loanService.getById(loanRemarksDto.loanId());

        if(!loans.getLoanStatus().equals(LoanStatus.PENDING) )
            throw new AccountRemarksException("Account is already verified and remarks cannot be added");


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


        loans.setCollatralStatus(CollatralStatus.valueOf(loanRemarksDto.CollatralStatus()));

        loanRepository.save(loans);
        System.out.println(user.getRole());
        Remarks remarks = new Remarks();
        remarks.setRole(user.getRole());
        remarks.setRemarks(loanRemarksDto.remarks());
        remarks.setRemarkStatus(RemarkStatus.ACTIVE);
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

    public void  UpadteRemarksStatus(RemarkStatus status, long id, String name) {


        Remarks remarks = remarksReposiotry.findById(id).orElseThrow(()-> new AccountRemarksException("Account is invalid"));

        if((remarks.getAccounts() == null ||
                !remarks.getAccounts().getCustomers().getUsers().getUsername().equals(name))
                &&
                (remarks.getLoans() == null ||
                        !remarks.getLoans().getCustomers().getUsers().getUsername().equals(name)))
            throw new AccountRemarksException("account is not valid");
        remarks.setRemarkStatus(status);

        remarksReposiotry.save(remarks);
    }

    public List<ViewRemarksDto> viewingAllLoanRemarks(String name) {

        // verify the user holds the account
        Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(users);
        List<Remarks> remarks = remarksReposiotry.viewingAllLoanRemarks(users.getUsername(),RemarkStatus.ACTIVE);

        System.out.println(remarks);
        return remarks.stream().map(RemarksMapper:: entToDto).toList();
    }
}

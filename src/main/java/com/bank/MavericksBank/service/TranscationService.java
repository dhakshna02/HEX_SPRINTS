package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.TranscationFlow;
import com.bank.MavericksBank.enums.TranscationStatus;
import com.bank.MavericksBank.enums.TranscationType;
import com.bank.MavericksBank.exceptions.AccountOwnerInvalidException;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.mapper.TranscationMapper;
import com.bank.MavericksBank.model.*;
import com.bank.MavericksBank.repository.LoanRepository;
import com.bank.MavericksBank.repository.TranscationRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TranscationService {
    private final UsersService usersService;
    private final AccountService accountService;
    private final TranscationRepository transcationRepository;
    private final CustomerService customerService;
    private final LoanRepository loanRepository;


    public Accounts getAccountdetails( long l, String userName) {

        Accounts accounts = accountService.getById(l);

        Users user = (Users) usersService.loadUserByUsername(userName);

        if(!accounts.getCustomers().getUsers().getUsername().equals(userName))
            throw new AccountOwnerInvalidException("Account is not owned by this customer");

        return  accounts;

    }


    // update the balance

    public void updateAccountBalance( long id, BigDecimal value) {

        accountService.depositupdateBalance(id,value);


    }

//    public void saveTranscation(Accounts account1, DepositDto depositDto) {
//
//        Transactions transaction = new Transactions();
//
//        transaction.setSourceAccount(account1);
//        transaction.setTranscationType(TranscationType.DEPOSIT);
//        transaction.setAmount(depositDto.value());
//        transaction.setRemarks(depositDto.remarks());
//        transaction.setBalanceAfterTranscation(account1.getBalance());
//        transaction.setTranscationStatus(TranscationStatus.COMPLETED);
//
//        transcationRepository.save(transaction);
//
//
//    }


    public void deposit(@Valid DepositDto depositDto, String name) {

         Accounts accounts =getAccountdetails(depositDto.accountId(),name);

        if(!accounts.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new AccountRemarksException("Account id invalid");
        updateAccountBalance(depositDto.accountId(),depositDto.value());
        Accounts account1 =  getAccountdetails(depositDto.accountId(),name);




        Transactions transaction = new Transactions();

        transaction.setSourceAccount(account1);
        transaction.setTranscationType(TranscationType.DEPOSIT);
        transaction.setAmount(depositDto.value());
        transaction.setRemarks(depositDto.remarks());
        transaction.setBalanceAfterTranscation(account1.getBalance());
        transaction.setTranscationflow(TranscationFlow.CREDIT);
        transaction.setTranscationStatus(TranscationStatus.COMPLETED);

        transcationRepository.save(transaction);

        System.out.println("done");
    }


    public void withdraw(@Valid WithdrawDto withdrawDto, String name) {

        // get the account and check weather tha account is acutallhy owned by the user
        Accounts account = getAccountdetails(withdrawDto.accountId(),name);

        // chek the accout status
        if(!account.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new AccountRemarksException("Account id invalid");

        // check accout withdraw is less than balance
        if(account.getBalance().subtract(withdrawDto.withdrawValue()).intValue() < 0 )
            throw new AccountRemarksException("Insufficent Balance");

        // update the balance
        accountService.withDrawUpdateAccountBalance(withdrawDto.accountId(),withdrawDto.withdrawValue());


        // taking the balance after transcation

        Accounts account1 = getAccountdetails(withdrawDto.accountId(),name);

        // save the transcagion

        Transactions transactions1 = new Transactions();

        transactions1.setSourceAccount(account1);
        transactions1.setTranscationType(TranscationType.WITHDRAW);
        transactions1.setTranscationStatus(TranscationStatus.COMPLETED);
        transactions1.setAmount(withdrawDto.withdrawValue());
        transactions1.setRemarks(withdrawDto.remarks());
        transactions1.setBalanceAfterTranscation(account1.getBalance());
       transactions1.setTranscationflow(TranscationFlow.DEBIT);
        //transactions1.setDestinationAccount("1");

        System.out.println(transactions1.toString());
        transcationRepository.save(transactions1);
        System.out.println("doner");



    }


    public void transferOtherBank(@Valid TransferDto transferDto, String name) {


        // get the account and check weather tha account is acutallhy owned by the user
        Accounts account = getAccountdetails(transferDto.sourceAccount(),name);




        // chek the accout status
        if(!account.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new AccountRemarksException("Account id invalid");

        // check accout withdraw is less than balance
        if(account.getBalance().subtract(transferDto.amount()).intValue() < 0 )
            throw new AccountRemarksException("Insufficent Balance");


        // subtract the money from the account

        accountService.debitAmountForOtherBankTransfer(transferDto);

        // taking the balance after transcation

        Accounts account1 = getAccountdetails(transferDto.sourceAccount(),name);

        // save to the transcation
        Transactions transactions = new Transactions();
        Accounts accountSource = new Accounts();
        accountSource.setId(transferDto.sourceAccount());

        transactions.setSourceAccount(accountSource);



        transactions.setDestinationAccount(transferDto.destinationAccount());

        transactions.setTranscationType(TranscationType.BANK_TRANSFER);
        transactions.setTranscationStatus(TranscationStatus.COMPLETED);
        transactions.setAmount(transferDto.amount());
        transactions.setRemarks(transferDto.remarks());

        transactions.setBalanceAfterTranscation(account1.getBalance());

        transactions.setIFSC(transferDto.ifsc());
        transactions.setTranscationflow(TranscationFlow.DEBIT);

        transcationRepository.save(transactions);



    }

    public InflowOutFlowDto TranscationInOutFlow(String name) {


        //final BigDecimal[] totalBalance = {BigDecimal.ZERO};
      Users user = (Users) usersService.loadUserByUsername(name);
      BigDecimal totalBalance = accountService.getAccountsByUserName(name);
      Customers customer = customerService.getByUsername(name);


      // last month start date
        LocalDateTime startOfLastMonth = LocalDate.now()
                .minusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        // start of this month


        LocalDateTime startOfThisMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        LocalDateTime end = LocalDateTime.now();



      List<Transactions> allTranscs = transcationRepository.getAllTransc(name,startOfLastMonth,end);





        List<Transactions> currentMonth = allTranscs.stream()
                .filter(t -> !t.getCreatedAt().isBefore(startOfThisMonth))
                .toList();

        List<Transactions> lastMonth = allTranscs.stream()
                .filter(t -> t.getCreatedAt().isBefore(startOfThisMonth)
                        && !t.getCreatedAt().isBefore(startOfLastMonth))
                .toList();

        BigDecimal totalDebit = BigDecimal.ZERO;

        for (Transactions t : currentMonth) {
            if (t.getTranscationflow() == TranscationFlow.DEBIT) {
                if (t.getAmount() != null) {
                    totalDebit = totalDebit.add(t.getAmount());
                }
            }
        }

        BigDecimal totalCredit = BigDecimal.ZERO;

        for (Transactions t : currentMonth) {
            if (t.getTranscationflow() == TranscationFlow.CREDIT) {
                if (t.getAmount() != null) {
                    totalCredit = totalCredit.add(t.getAmount());
                }
            }
        }



        BigDecimal totalDebitLastMonth = BigDecimal.ZERO;
        BigDecimal totalCreditLastMonth = BigDecimal.ZERO;

        for (Transactions t : lastMonth) {
            if (t.getTranscationflow() == TranscationFlow.DEBIT) {
                if (t.getAmount() != null) {
                    totalDebitLastMonth = totalDebitLastMonth.add(t.getAmount());
                }
            }
        }



        for (Transactions t : lastMonth) {
            if (t.getTranscationflow() == TranscationFlow.CREDIT) {
                if (t.getAmount() != null) {
                    totalCreditLastMonth = totalCreditLastMonth.add(t.getAmount());
                }
            }
        }







        return new InflowOutFlowDto(
                 totalBalance,
                 totalDebit,
                 totalCredit,
                customer.getName(),
                totalCreditLastMonth,
                totalDebitLastMonth

        );
    }

    public getAllTranscationssDto getAllTrascation(String name, int page, int size, LocalDate fromDate, LocalDate toDate) {



        LocalDate today = LocalDate.now();

        if (fromDate == null) {
            fromDate = today.minusDays(10);  // ✅ last 10 days
        }

        if (toDate == null) {
            toDate = today; // ✅ today
        }
        Users users = (Users) usersService.loadUserByUsername(name);


        System.out.println(fromDate);
        System.out.println(toDate.atTime(23,59,59));
        // transcationRepository.getAllTranscations(acctIds);

        Pageable pageable = PageRequest.of(page,size);
        int totalpages = 0;
        long totalsize = 0 ;
         List<Transactions> transactions = new ArrayList<>();

           Page<Transactions> transactions1 =   transcationRepository.getAllTranscations(name,pageable,fromDate.atStartOfDay(),toDate.atTime(23,59,59));


           List<GetAllTranscationDto> transcationDto =  transactions1.toList().stream().map(TranscationMapper::TransEntToDto).toList();


            return  new getAllTranscationssDto(
                    transcationDto,
                    transactions1.getTotalPages(),
                    transactions1.getTotalElements()
            );
         }


    public void transferInsideBank(@Valid TransferInsideDto transferInsideDto, String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        if(transferInsideDto.sourceAccount() == transferInsideDto.destinationAccount())
            throw new AccountRemarksException("soucre and destination accoubt are same u can do ahead please check again!!");

        Accounts sourceAccount= accountService.getById(transferInsideDto.sourceAccount());
        Accounts destinationAccount = accountService.getById(transferInsideDto.destinationAccount());


        System.out.println(sourceAccount);
        System.out.println(destinationAccount);
        if(!sourceAccount.getCustomers().getUsers().getUsername().equals(users.getUsername()))
            throw new AccountOwnerInvalidException("Account is not owned by you");



        // check the balance from the source account
        if(sourceAccount.getBalance().compareTo(transferInsideDto.amount())<0)
            throw  new AccountRemarksException("Invalid Balance");

// withdraw amount from the account
        accountService.withDrawUpdateAccountBalance(transferInsideDto.sourceAccount(), transferInsideDto.amount());
        // deposit money in the account
        accountService.depositupdateBalance(transferInsideDto.destinationAccount(), transferInsideDto.amount());

        //setting transcation for withdraw account
        Transactions transactions = new Transactions();
        transactions.setSourceAccount(sourceAccount);
        transactions.setDestinationAccount(String.valueOf(destinationAccount.getId()));
        transactions.setTranscationType(TranscationType.BANK_TRANSFER);
        transactions.setTranscationStatus(TranscationStatus.COMPLETED);
        transactions.setAmount(transferInsideDto.amount());
        transactions.setRemarks(transferInsideDto.remarks());
        Accounts account2 = getAccountdetails(transferInsideDto.sourceAccount(),name);

        transactions.setBalanceAfterTranscation(account2.getBalance());

        transactions.setTranscationflow(TranscationFlow.DEBIT);

        transcationRepository.save(transactions);


        // setting the transcation for deposit account

        Transactions transactions1 = new Transactions();
        transactions1.setSourceAccount(destinationAccount);

        transactions1.setTranscationType(TranscationType.BANK_TRANSFER);
        transactions1.setTranscationStatus(TranscationStatus.COMPLETED);
        transactions1.setAmount(transferInsideDto.amount());
        transactions1.setRemarks(transferInsideDto.remarks());
        Accounts account3 = accountService.getById(destinationAccount.getId());
        transactions1.setBalanceAfterTranscation(account3.getBalance());

        transactions1.setTranscationflow(TranscationFlow.CREDIT);

        transcationRepository.save(transactions1);




    }

    public void loanDisbursement(long l, String username) {
        System.out.println(l);
        Loans loans = loanRepository.findById(l).orElseThrow(()-> new AccountRemarksException("Loan invalid"));

        List<Accounts>  accounts = accountService.getAllAccountsByUserName(username);
        System.out.println(accounts);

       Accounts accounts2 =  accounts.stream()
               .filter(accounts1 -> accounts1.getAccountStatus().equals(AccountStatus.ACTIVE))
               .findFirst()
               .orElse(null);

       // adding the money to the account

        System.out.println(loans.getApprovedLoanAmount());
        System.out.println(accounts2.getBalance());
        System.out.println(accounts2);

        //accounts2.getBalance().add(loans.getApprovedLoanAmount());


        DepositDto depositDto = new DepositDto(
                accounts2.getId(),
                loans.getApprovedLoanAmount(),
                "Loan Disbursment"
        )  ;

        deposit(depositDto,username);



    }
}

package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.model.Accounts;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
    @Query("""
            select a
            from Accounts a
            where a.accountOpeningStatus =?2
            AND a.employees.users.userName =?1 or a.customers.users.userName =?1
            """)
    Page<Accounts> getAllUnverfiedAccounts(String name, AccountOpeningStatus accountOpeningStatus,Pageable pageable);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            update Accounts a
            set a.balance = a.balance+?2
            where a.id =?1
            """)
    void depositupdateBalance( @NotNull long l,  @NotNull BigDecimal value);



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            update Accounts a
            set a.balance = a.balance-?2
            where a.id =?1
            """)
    void withDrawUpdateAccountBalance(@NotNull long l, @NotNull @Positive BigDecimal withdrawValue);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            update Accounts a
            set a.balance = a.balance-?2
            where a.id=?1
            """)
    void debitAmountForOtherBankTransfer(@NotNull long l, @NotNull @Positive BigDecimal amount);



    @Query("""
            select sum(a.balance) from Accounts a
            where a.customers.users.userName =?1
            """)
    BigDecimal getAccountsByUserName(String name);


    @Query("""
            select a from Accounts a
            where a.customers.users.userName =?1 AND
            a.accountType = ?2
            """)
    List<Accounts> getSavingsWidget(String name, AccountType accountType);


    @Query("""
            select a from Accounts a
            where a.customers.users.userName =?1
            """)
    List<Accounts> getAllAccountsByUserName(String name);



    @Query("""
            select a from Accounts a
            where a.employees IS NULL
            
            """)
    Page<Accounts> getAllUnverfiedAccountsWithNoVerifierId(Pageable pageable);


    @Query("""
            select count(a)
            from Accounts a
            where a.accountStatus =?1
            """)
    int getNoOfActiveAccounts(AccountStatus active);



    @Query("""
            select count(a)
            from Accounts a
            where a.accountOpeningStatus =?1
            """)
    int getNoOfAcctsInitated(AccountOpeningStatus accountStatus);


    // List<Accounts> findByNameContainingIgnoreCase(String keyword);
}

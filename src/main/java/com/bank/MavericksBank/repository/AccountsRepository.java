package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.GettingAllUnfiredAccountsWithCustomerDetails;
import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
    @Query("""
            select a.id,
            a.accountType,
            a.balance,
            a.openDate,
            a.accountOpeningStatus,
            a.customers.name
            from Accounts a
            where a.accountOpeningStatus =?1
            """)
    List<GettingAllUnfiredAccountsWithCustomerDetails> getAllUnverfiedAccounts(AccountOpeningStatus accountOpeningStatus);
}

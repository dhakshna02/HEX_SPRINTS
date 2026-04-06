package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.MultiAccountBalanceDto;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Customers;
import org.hibernate.annotations.DialectOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customers,Long> {

    @Query("""
    select a.id , a.balance from Accounts a
    where a.customers.id = ?1
""")
    List<MultiAccountBalanceDto> getAllAccountBalace(long id);


    @Query("""
            select c from Customers c
            where c.users.userName = ?1
            """)
    Customers getByUsername(String username);




}

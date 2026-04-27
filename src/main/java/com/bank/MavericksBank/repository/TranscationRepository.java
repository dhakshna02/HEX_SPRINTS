package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TranscationRepository extends JpaRepository<Transactions,Long> {

    @Query("""
            select t from Transactions t
            where t.sourceAccount.customers.users.userName =?1
            AND t.createdAt between ?2 AND ?3
            """)
    List<Transactions> getAllTransc(String name,LocalDateTime start, LocalDateTime end);


    @Query("""
            select t from Transactions t
            where t.sourceAccount.customers.users.userName IN ?1
             AND t.createdAt between ?2 AND ?3
            ORDER BY t.id DESC
            """)
    Page<Transactions> getAllTranscations(String name, Pageable pageable, LocalDateTime fromDate, LocalDateTime toDate);
}

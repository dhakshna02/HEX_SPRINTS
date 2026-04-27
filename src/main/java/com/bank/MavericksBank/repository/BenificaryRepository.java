package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Benificaries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BenificaryRepository extends JpaRepository<Benificaries,Long> {

    @Query("""
            select b from Benificaries b 
            where b.customers.users.userName =?1
            """)
    Page<Benificaries> getByUserName(String name, Pageable pageable);
}

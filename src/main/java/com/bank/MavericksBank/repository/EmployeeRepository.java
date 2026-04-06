package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employees,Long> {

    @Query("""
            select e.id from Employees e
            where e.users.userName = ?1
            """)
    Employees getByUserName(String name);
}

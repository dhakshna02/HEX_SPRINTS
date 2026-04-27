package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.enums.Designation;
import com.bank.MavericksBank.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employees,Long> {

    @Query("""
            select e from Employees e
            where e.users.userName = ?1
            """)
    Employees getByUserName(String name);


    @Query("""
            select e from Employees e
            where e.designation =?1
            """)
    List<Employees> getAllManager(Designation designation);


}

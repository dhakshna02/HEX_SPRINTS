package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employees,Long> {
}

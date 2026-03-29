package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.GetLoanForEmployeeDto;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.model.Loans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loans,Long> {


   // List<GetLoanForEmployeeDto> findAllByLoansWhichArePending(String pending);

    @Query("""
            select l.id,
            l.accountType,
            l.amount,
            l.intrestRate,
            l.months,
            l.emi,
            l.loanStatus,
            l.customers.id
            from Loans l where l.loanStatus = ?1
            """)
    List<GetLoanForEmployeeDto> findAllbyPendingLoanApproval( LoanStatus pending);
}

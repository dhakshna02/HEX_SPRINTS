package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.GetLoanForEmployeeDto;
import com.bank.MavericksBank.dto.LoanCustomerDto;
import com.bank.MavericksBank.dto.LoanResponseDto;
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
            l.loanType,
            l.requestedLoanAmount,
            l.intrestRate,
            l.months,
            l.emi,
            l.loanStatus,
            l.customers.id
            from Loans l where l.loanStatus = ?1
            """)
    List<GetLoanForEmployeeDto> findAllbyPendingLoanApproval( LoanStatus pending);


    @Query("""
            select
                l.customers.name,
                l.customers.mailId,
                l.customers.address,
                l.customers.gender,
                l.customers.occupation,
                l.customers.annualIncome,
                l.customers.identityProof,
                l.customers.addressProof,
                l.customers.panNo,
                l.customers.incomeCertificate,
                l.customers.photograph
                from Loans l
                where l.customers.users.userName =?1
            """)
    List<LoanCustomerDto> getByUserDetails(String name);


    @Query("""
        SELECT
            l.id,
            l.loanType,
            l.requestedLoanAmount,
            l.approvedLoanAmount,
            l.months,
            l.emi,
            l.loanStatus
        FROM Loans l
        where l.customers.users.userName =?1
""")
    List<LoanResponseDto> getAllLoanDetails(String name);
}

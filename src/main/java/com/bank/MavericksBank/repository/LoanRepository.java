package com.bank.MavericksBank.repository;

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
            select l
            from Loans l where l.loanStatus = ?1
            AND l.employees IS NULL
            """)
    Page<Loans> findAllbyPendingLoanApproval(LoanStatus pending, Pageable pageable);


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
            l.loanStatus,
            l.intrestRate,
            l.LoanBalance,
            l.approvedAt,
             l.customers.name
        FROM Loans l
        where l.customers.users.userName =?1
""")
    List<LoanResponseDto> getAllLoanDetails(String name);


    @Query("""
            select l from Loans l
            where l.customers.users.userName = ?1
            """)
    List<Loans> getByUserName(String name);


    @Query("""
        SELECT
            l.id,
            l.loanType,
            l.requestedLoanAmount,
            l.approvedLoanAmount,
            l.months,
            l.emi,
            l.loanStatus,
            l.intrestRate,
            l.LoanBalance,
            l.approvedAt,
            l.customers.name
        FROM Loans l
        where l.id =?1
""")
    List<LoanResponseDto> getAllLoanDetailsById(long id, String name);


    @Query("""
            select l from Loans l
            where l.financialAnalystId =?1 and
            l.loanStatus =?2 AND
            l.riskRating IS NULL
            """)
    Page<Loans> getByFinancialAnalystId(long id, LoanStatus PENDING, Pageable pageable);

    @Query("""
            select l from Loans l
            where l.customers.id =?1 AND
            l.loanStatus =?2
            """)
    List<Loans> getExistingActiveLoansOfCustomer(long customerid, LoanStatus loanStatus);

    @Query("""
            select l from Loans l
            where l.assestVerifierId =?1 and
            l.loanStatus =?2
            """)
    Page<Loans> getByAssestVeriferId(long id, LoanStatus PENDING, Pageable pageable);

    @Query("""
            select l from Loans l
            where l.employees.users.userName = ?1
            AND  l.loanStatus =?2
            """)
    Page<Loans> getByUserNameInEmp(String name, LoanStatus PENDING, Pageable pageable);


    @Query("""
            select count(l)
            from Loans l
            where l.loanStatus =?1
            """)
    int getNoOfLoansOnGoing(LoanStatus loanStatus);
}

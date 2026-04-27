package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.enums.RemarkStatus;
import com.bank.MavericksBank.model.Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RemarksReposiotry extends JpaRepository<Remarks,Long> {

    @Query("""
            select r
            from Remarks r
            where r.accounts.customers.users.userName =?1
            AND r.remarkStatus =?2
           """)
    List<Remarks> viewingAllRemarks(String  name , RemarkStatus active);


    @Query("""
            select r.role,
            r.remarks
            from Remarks r
            where r.loans.id = ?1
           """)
    List<ViewRemarksDto> viewingAllRemarksForLoan(long lid);


    @Query("""
            select r
            from Remarks r
            where r.loans.customers.users.userName =?1
            AND r.remarkStatus =?2
           """)
    List<Remarks> viewingAllLoanRemarks(String username, RemarkStatus remarkStatus);
}

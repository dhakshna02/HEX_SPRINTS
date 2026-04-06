package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.model.Remarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RemarksReposiotry extends JpaRepository<Remarks,Long> {

    @Query("""
            select r.role,
            r.remarks
            from Remarks r
            where r.accounts.id = ?1
           """)
    List<ViewRemarksDto> viewingAllRemarks(long aid);


    @Query("""
            select r.role,
            r.remarks
            from Remarks r
            where r.loans.id = ?1
           """)
    List<ViewRemarksDto> viewingAllRemarksForLoan(long lid);
}

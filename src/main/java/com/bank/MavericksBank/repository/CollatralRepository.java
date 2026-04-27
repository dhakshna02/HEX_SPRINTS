package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.model.Collatral;
import com.bank.MavericksBank.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollatralRepository extends JpaRepository<Collatral,Long> {

   @Query("""
           select
           c.loans.id,
           c.id,
           c.collataralname,
           c.collatralType,
           c.collatralAddress,
           c.collatralDocument
            from Collatral c
            where c.loans.id = ?1
            and c.collatralValye IS NULL
           """)
    CollatralResponseDto getAllCollatralsById(long lid);


   @Query("""
           select c from Collatral c
           where c.loans = ?1
           """)
    List<Collatral> getbyLoanId(Loans id);
}

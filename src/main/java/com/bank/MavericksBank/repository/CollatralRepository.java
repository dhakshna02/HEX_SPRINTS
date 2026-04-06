package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.model.Collatral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollatralRepository extends JpaRepository<Collatral,Long> {

   @Query("""
           select
           c.id,
           c.collataralname,
           c.collatralType,
           c.collatralValye,
           c.collatralAddress
            from Collatral c
            where c.loans.id = ?1
           """)
    List<CollatralResponseDto> getAllCollatralsById(long lid);
}

package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranscationRepository extends JpaRepository<Transactions,Long> {
}

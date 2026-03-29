package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts,Long> {
}

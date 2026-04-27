package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}

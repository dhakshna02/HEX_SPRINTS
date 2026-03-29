package com.bank.MavericksBank.repository;

import com.bank.MavericksBank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<Users,Long> {
    @Query("""
            select u from Users u where u.userName = ?1
            """)
    Users findByUserNamer(String username);
}

package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.AccountOpeningStatus;
import com.bank.MavericksBank.enums.AccountStatus;
import com.bank.MavericksBank.enums.AccountType;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;


    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;


    private BigDecimal balance  ;

    @CreationTimestamp
    @Column(name = "open_date",updatable = false)
    private LocalDate openDate;

    @Column(name = "account_opening_status")
    @Enumerated(EnumType.STRING)
    private AccountOpeningStatus accountOpeningStatus;


    @Column(name = "account_verified_date")
    private LocalDate accountVerifiedDate;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @ManyToOne
    private Customers customers;


    @ManyToOne
    @JoinColumn(name = "account_verified_by_employee")
    private Employees employees;
}

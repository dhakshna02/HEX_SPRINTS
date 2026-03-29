package com.bank.MavericksBank.model;


import com.bank.MavericksBank.enums.AccountType;
import com.bank.MavericksBank.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.IdentityHashMap;

@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Enumerated(EnumType.STRING)
    @Column(name = "accout_type")
    private AccountType accountType;

    private BigDecimal amount;

    @Column(name = "intrest_rate")
    private BigDecimal intrestRate;

    private int months;

    private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status")
    private LoanStatus loanStatus;

    @ManyToOne
    private Customers customers;


    @ManyToOne
    @JoinColumn(name = "loan_approved_by_emp")
    private Employees employees;


}


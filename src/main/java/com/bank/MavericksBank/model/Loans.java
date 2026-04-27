package com.bank.MavericksBank.model;


import com.bank.MavericksBank.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type")
    private LoanType loanType;

    @Column(name = "requested_loan_amount")
    private BigDecimal requestedLoanAmount;

    @Column(name = "approved_loan_amount")
    private BigDecimal approvedLoanAmount;

    @Column(name = "intrest_rate")
    private BigDecimal intrestRate;

    private int months;

    private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status")
    private LoanStatus loanStatus;


    @Enumerated(EnumType.STRING)
    private RiskRating riskRating;

    @Column(name = "financial_analyst_id")
    private long financialAnalystId;

    @Column(name = "asset_verifier_id")
    private long assestVerifierId;

    @Enumerated(EnumType.STRING)
    private CustomerLoanDecision customerLoanDecision;

    @ManyToOne
    private Customers customers;

    @Enumerated(EnumType.STRING)
    private CollatralStatus collatralStatus;


    @Column(name = "loan_balance")
    private BigDecimal LoanBalance;

    @ManyToOne
    @JoinColumn(name = "loan_verifier")
    private Employees employees;

    @Column(name = "approved_at")
    private LocalDate approvedAt;







}


package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.TranscationStatus;
import com.bank.MavericksBank.enums.TranscationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transcations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "source_account")
    private long sourceAccount;

    @Column(name = "destination_account")
    private long destinationAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transcation_type")
    private TranscationType transcationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transcation_status")
    private TranscationStatus transcationStatus;

    private BigDecimal amount;

    private String remarks;

    @ManyToOne
    private Accounts accounts;


}

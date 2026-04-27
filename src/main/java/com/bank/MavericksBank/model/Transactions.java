package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.TranscationFlow;
import com.bank.MavericksBank.enums.TranscationStatus;
import com.bank.MavericksBank.enums.TranscationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transcations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "source_account")
    private Accounts sourceAccount;


    @Column(name = "destination_account",nullable = true)
    private String destinationAccount;


    @Enumerated(EnumType.STRING)
    @Column(name = "transcation_type")
    private TranscationType transcationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transcation_status")
    private TranscationStatus transcationStatus;

    private BigDecimal amount;

    private String remarks;

    @Column(name = "balance_after_transcation")
    private BigDecimal balanceAfterTranscation;

    @Column(name = "ifsc")
    private String IFSC;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    private TranscationFlow transcationflow;

}

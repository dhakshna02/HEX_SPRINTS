package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.CollatralTypes;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "collatral")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Collatral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String collataralname;

    @Enumerated(EnumType.STRING)
    private CollatralTypes collatralType;

    private BigDecimal collatralValye;

    private String collatralAddress;

    @Column(name = "collatral_doucment")
    private String collatralDocument;

    @ManyToOne
    private Loans loans;
}

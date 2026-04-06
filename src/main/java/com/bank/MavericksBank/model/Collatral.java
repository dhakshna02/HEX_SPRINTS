package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.CollatralTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "collatral")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Collatral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String collataralname;

    @Enumerated(EnumType.STRING)
    private CollatralTypes collatralType;

    private BigDecimal collatralValye;

    private String collatralAddress;

    @ManyToOne
    private Loans loans;
}

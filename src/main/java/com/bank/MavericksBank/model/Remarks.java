package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "remarks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Remarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loans loans;

}

package com.bank.MavericksBank.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "benificaries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Benificaries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ifsc")
    private String IFSC;

    @Column(name="account_number")
    private Long accountNumber;

    @Column(name = "payee_name")
    private String payeeName;

    @ManyToOne
    private Customers customers;


}

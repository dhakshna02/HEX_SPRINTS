package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "mail_id")
    private String mailId;

    @Column(length = 1000)
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate DateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String occupation;

    @Column(name = "annual_income")
    private long annualIncome;


    @Column(name = "identity_proof")
    private String identityProof;

    @Column(name = "address_proof")
    private String addressProof;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "income_certificate")
    private String incomeCertificate;

    private String photograph;

    private String signature;



    @OneToOne
    private  Users users;

}

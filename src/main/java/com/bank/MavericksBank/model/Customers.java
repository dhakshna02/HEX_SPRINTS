package com.bank.MavericksBank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "aadhar_no")
    private String aadharNo;

    @OneToOne
    private  Users users;

}

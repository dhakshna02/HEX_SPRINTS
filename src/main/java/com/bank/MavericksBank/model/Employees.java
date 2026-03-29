package com.bank.MavericksBank.model;

import com.bank.MavericksBank.enums.Designation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.IdentityHashMap;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    @Column(name = "mob_no")
    private String mobNo;


    @Enumerated(EnumType.STRING)
    private Designation designation;

    @OneToOne
    private Users users;

}

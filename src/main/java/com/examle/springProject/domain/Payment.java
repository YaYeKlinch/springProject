package com.examle.springProject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String property;
    private String purpose;

    @OneToMany(mappedBy = "payment" , fetch = FetchType.LAZY)
    Set<UserPayment> userPayments;
}

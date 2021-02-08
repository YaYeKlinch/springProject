package com.examle.springProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "credit_card")
@Getter
@Setter
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cvv;
    private Long number;
    private int pin;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable=false)
    Account account;

    @OneToMany(mappedBy = "creditCard" , fetch = FetchType.EAGER)
    Set<UserPayment> userPayments;
}

package com.examle.springProject.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String number;

    private boolean blocked;

    @Column(nullable = false)
    @NotNull
    private int costs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private  Set<CreditCard> creditCards;

    public Account() {
    }

    public Account(Long id, String name, String number, boolean blocked, int costs, User owner) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.blocked = blocked;
        this.costs = costs;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return blocked == account.blocked && costs == account.costs && name.equals(account.name) && number.equals(account.number) && owner.equals(account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, blocked, costs, owner);
    }
}
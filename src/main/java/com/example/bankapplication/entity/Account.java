package com.example.bankapplication.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "account")
@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "Account.findAccountByCardNumber",query = "SELECT * FROM account where cardNumber=:anum"
                ,resultClass = Account.class
        )
})
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


@NotNull
    @Column(nullable = false,unique = true)
    private Long cardNumber;

    @NotNull
    @Column(nullable = false,name = "account_num")
    private Long accountNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
@Range(min = 1000,max = 9999)
    @NotNull

    private Integer cvv2;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date expireDate;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<LastThreeTransaction> transactionSet;

    @Column
    @PositiveOrZero
    private Double balance;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", accountNumber=" + accountNumber +
                ", accountType=" + accountType +
                ", cvv2=" + cvv2 +
                ", expireDate=" + expireDate +
                ", transactionSet=" + transactionSet +
                ", balance=" + balance +
                '}';
    }
}

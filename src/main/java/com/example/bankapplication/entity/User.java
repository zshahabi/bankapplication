package com.example.bankapplication.entity;

import javax.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_tbl")
@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "User.findByName",query = "SELECT * FROM user_tbl where firstname=:fname"
        ,resultClass = User.class
        ) ,  @NamedNativeQuery(name = "User.findByLastName",query = "SELECT * FROM user_tbl where lastname=:lname"
        ,resultClass = User.class
        ),
        @NamedNativeQuery(name = "User.findByNationalCode",query = "SELECT * FROM user_tbl where nationalcode=:ncode"
                ,resultClass = User.class
        )


})
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable=false, unique=true)

@Min(value = 100_000_000_0L)
    @Max(value = 999_999_999_9L)
    @Positive
    private Long nationalCode;
    @Column(nullable=false)
    @NotNull
    @NotEmpty
    private String firstname;
    @Column(nullable=false)
    @NotNull
    @NotEmpty
    private String lastname;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Account> accounts;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date userCreationDate;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<LastThreeUpdate> lastThreeUpdates;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nationalCode=" + nationalCode +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", accounts=" + accounts +
                ", userType=" + userType +
                ", userCreationDate=" + userCreationDate +
                ", lastThreeUpdates=" + lastThreeUpdates +
                '}';
    }
}

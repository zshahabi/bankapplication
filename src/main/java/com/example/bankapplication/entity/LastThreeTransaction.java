package com.example.bankapplication.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.*;

@Entity(name = "lastthreetransaction")
@Getter
@Setter

public class LastThreeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventType event;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date eventDate;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)

    private Account account;





}

package com.example.bankapplication.entity;

import javax.validation.constraints.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Date;

@Entity(name = "lastthreeupdate")
@Getter
@Setter
public class LastThreeUpdate {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO )
        private Long id;

        @NotNull
        @Enumerated(EnumType.STRING)
        private UpdateType event;
        @NotNull
        @Temporal(TemporalType.DATE)
        private Date eventDate;
    @ManyToOne()
    @JoinColumn(name="user_id", nullable=false)

    private User user;
}

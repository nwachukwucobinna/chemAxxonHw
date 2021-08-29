package com.odana.CostRegister.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "costs")
@Getter @Setter @NoArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Cost(float amount, String user, LocalDateTime timestamp) {
        this.amount = amount;
        this.user = user;
        this.timestamp = timestamp;
    }
}

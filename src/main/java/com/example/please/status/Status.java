package com.example.please.status;

import com.example.please.user.StatusEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private StatusEnum name;

    @Column(name = "start_at")
    private LocalDate startedAt;

    @Column(name = "end_at")
    private LocalDate endedAt;

    //user
}

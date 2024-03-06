package com.example.please.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "at_work")
    private byte atWork;

    @Column(name = "time_coming")
    private LocalTime timeComing;

//    @Column(name = "room")
//    private int room = 666;
//
//    @Column(name = "phone_number")
//    private String phoneNumber = "";
}

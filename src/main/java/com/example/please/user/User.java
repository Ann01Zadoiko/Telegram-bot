package com.example.please.user;


import com.example.please.notification.Notification;
import com.example.please.status.Status;
import com.example.please.status.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
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

    @Column(name = "time_of_coming")
    private LocalTime timeComing;

    @Column(name = "room")
    private Integer room;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Status status1;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", atWork=" + atWork +
                ", timeComing=" + timeComing +
                ", room=" + room +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

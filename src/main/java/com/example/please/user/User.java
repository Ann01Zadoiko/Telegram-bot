package com.example.please.user;


import com.example.please.notification.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
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

    @Column(name = "at_work")
    private byte atWork;

    @Column(name = "time_of_coming")
    private LocalTime timeComing;

    @Column(name = "phone_number")
    private String phoneNumber;

  //  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", fullName='" + fullName + '\'' +
                ", atWork=" + atWork +
                ", timeComing=" + timeComing +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + statusEnum +
                '}';
    }

}

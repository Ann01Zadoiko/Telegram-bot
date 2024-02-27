package com.example.please.user;

import com.example.please.atWork.AtWork;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "departure")
    private String departure;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private AtWork atWork;

    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", fullName='" + fullName + '\'' +
                ", departure='" + departure  +  '}';
    }
}

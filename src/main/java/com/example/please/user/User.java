package com.example.please.user;

import com.example.please.atWork.AtWork;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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

    @Column(name = "username")
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "departure")
    private String departure;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AtWork atWork;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", departure='" + departure  +  '}';
    }
}

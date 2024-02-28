package com.example.please.user;

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

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName +  '}';
    }
}

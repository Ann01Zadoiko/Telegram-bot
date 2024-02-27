package com.example.please.atWork;

import com.example.please.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "list_of_employees")
public class AtWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date = LocalDate.now();


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

//    @OneToMany(mappedBy = "atWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<User> users;

    @Override
    public String toString() {
        return "AtWork{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}


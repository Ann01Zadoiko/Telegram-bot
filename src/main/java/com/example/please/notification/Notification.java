package com.example.please.notification;

import com.example.please.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_of_notification")
    private String timeOfNotification;

    @Column(name = "turn_on")
    private Boolean turnOn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", timeOfNotification='" + timeOfNotification + '\'' +
                ", turnOn=" + turnOn +
                ", user=" + user +
                '}';
    }
}

package com.example.please.notification;

import com.example.please.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(nativeQuery = true, value = "select * from notifications")
    List<Notification> list();

    @Query("select n from Notification n where n.user = :user")
    Notification findNotificationByUser(User user);
}

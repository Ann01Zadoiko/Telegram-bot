package com.telegram.notification;

import com.telegram.user.User;

import java.util.List;

public interface INotificationService {

    void save(Notification notification);

    Notification getNotificationByUser(User user);

    List<Notification> findAll();
}

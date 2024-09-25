package com.example.please.notification;

import com.example.please.user.User;

public interface INotificationService {

    void save(Notification notification);

    Notification getNotificationByUser(User user);
}

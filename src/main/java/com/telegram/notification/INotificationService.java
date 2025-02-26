package com.telegram.notification;

import com.telegram.user.User;

public interface INotificationService {

    void save(Notification notification);

    Notification getNotificationByUser(User user);
}

package com.telegram_bot.notification;

import com.telegram_bot.user.User;

public interface INotificationService {

    void save(Notification notification);

    Notification getNotificationByUser(User user);
}

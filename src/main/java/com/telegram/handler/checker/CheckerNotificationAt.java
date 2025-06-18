package com.telegram.handler.checker;

import com.telegram.notification.Notification;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;

public class CheckerNotificationAt {

    private CheckerNotificationAt(){}

    public static boolean isNotificationAt(User user, Notification notification, String time){
        return (user.getAtWork() == 0 && notification.getTurnOn()
                && notification.getTimeOfNotification().equals(time) &&
                (user.getStatusEnum().equals(StatusEnum.WORK) || user.getStatusEnum().equals(StatusEnum.REMOTE)));
    }
}

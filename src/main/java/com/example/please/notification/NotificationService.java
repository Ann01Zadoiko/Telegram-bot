package com.example.please.notification;

import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService implements INotificationService{

    private final NotificationRepository notificationRepository;

    //save or update
    public void save(Notification notification){

        notificationRepository.save(notification);

        log.info("\nNew notification: " + notification);
    }

    //get the notification by the user
    public Notification getNotificationByUser(User user){
        Notification notification = notificationRepository.findNotificationByUser(user);

        log.info("User: " + notification.getUser());

        return notification;
    }

}

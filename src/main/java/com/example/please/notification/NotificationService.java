package com.example.please.notification;

import com.example.please.user.User;
import com.example.please.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void save(Notification notification){

        notificationRepository.save(notification);

        log.info("\nNew notification: " + notification);
    }

    public List<Notification> getList(){
        log.info("\nList: " + notificationRepository.findAll());

        return notificationRepository.findAll();
    }

    public List<Notification> list(){
        log.info("\nList: " + notificationRepository.findAll());

        return notificationRepository.findAll();
    }

    public Notification getNotificationByUser(User user){
        Notification notification = notificationRepository.findNotificationByUser(user);

        log.info("User: " + notification.getUser());

        return notification;
    }

}

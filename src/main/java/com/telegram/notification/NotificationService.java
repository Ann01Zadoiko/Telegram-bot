package com.telegram.notification;

import com.telegram.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NotificationService implements INotificationService{

    private final NotificationRepository notificationRepository;

    //save or update
    public void save(Notification notification){

        notificationRepository.save(notification);

        log.info("New notification: {}", notification);
    }

    //get the notification by the user
    public Notification getNotificationByUser(User user){
        Notification notification = notificationRepository.findNotificationByUser(user);
        return notification;
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

}

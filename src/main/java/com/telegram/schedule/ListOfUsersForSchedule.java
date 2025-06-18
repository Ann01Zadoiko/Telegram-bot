package com.telegram.schedule;

import com.telegram.bot.TelegramBot;
import com.telegram.config.BotConfig;
import com.telegram.constant.Phrases;
import com.telegram.handler.checker.CheckerNotificationAt;
import com.telegram.handler.registration.Registration;
import com.telegram.handler.registration.UserStateManager;
import com.telegram.notification.Notification;
import com.telegram.notification.NotificationService;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListOfUsersForSchedule {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;
    private final Registration registration;
    private final UserStateManager stateManager;

    @SneakyThrows
    public void listOfUserForSchedule(String time){
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (CheckerNotificationAt.isNotificationAt(user, notification, time)) {
                new TelegramBot(config, userService, notificationService, registration, stateManager).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info("{} got the notification at {}", user.getFullName(), time);
        }
    }
}

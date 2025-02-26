package com.example.please.schedule;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.Phrases;
import com.example.please.handler.MessageChecker;
import com.example.please.handler.registration.Registration;
import com.example.please.handler.registration.UserStateManager;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import com.example.please.user.UserService;
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

            if (MessageChecker.isNotificationAt(user, notification, time)) {
                new TelegramBot(config, userService, notificationService, registration, stateManager).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info("{} got the notification at {}", user.getFullName(), time);
        }
    }
}

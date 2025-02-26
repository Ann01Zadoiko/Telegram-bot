package com.telegram_bot.schedule;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.config.BotConfig;
import com.telegram_bot.constant.Phrases;
import com.telegram_bot.handler.MessageChecker;
import com.telegram_bot.handler.registration.Registration;
import com.telegram_bot.handler.registration.UserStateManager;
import com.telegram_bot.notification.Notification;
import com.telegram_bot.notification.NotificationService;
import com.telegram_bot.user.User;
import com.telegram_bot.user.UserService;
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

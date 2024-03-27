package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.NotificationTime;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Schedules {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    @Scheduled(cron = "0 0 0 * * *")
    public void becomeNewDay() {
        List<User> users = userService.listAll();
        log.info("A new day!");

        for (User user : users) {
            user.setAtWork((byte) 0);
            userService.update(user);
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.NINE)
    public void dailyRememberAtNine() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);
            if (MessageChecker.isNotificationAt(user, notification, "9")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), "Запізнюватись не гарно!");
            }
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT)
    public void dailyRememberAtEight() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);
            if (MessageChecker.isNotificationAt(user, notification, "8")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), "Запізнюватись не гарно!");
            }
        }
    }
}

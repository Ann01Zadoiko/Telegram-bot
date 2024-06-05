package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.NotificationTime;
import com.example.please.constant.Phrases;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;

import com.example.please.user.StatusEnum;
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


    //reset the list
    @Scheduled(cron = "0 0 0 * * *")
    public void becomeNewDay() {
        List<User> users = userService.listAll();
        log.info("A new day!");

        for (User user : users) {
            user.setAtWork((byte) 0);
            userService.save(user);
        }
    }

    //the notification at 9 am
    @SneakyThrows
    @Scheduled(cron = NotificationTime.NINE)
    public void dailyRememberAtNine() {
        List<User> users = userService.listAll();

        for (User user : users) {

            if (user.getAtWork() != 1 &&
                    (user.getStatusEnum().equals(StatusEnum.REMOTE) || user.getStatusEnum().equals(StatusEnum.WORK))
            ){
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.LATE);
            }

            log.info("9 am! Time to work!");
        }
    }

    //the notification at 8 am
    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT)
    public void dailyRememberAtEight() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:00")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8 am");
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT_FORTY)
    public void dailyRememberAtEightForty() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:40")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8:40");
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT_FIFTY)
    public void dailyRememberAtEightFifty() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:50")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8:50");
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT_FORTY_FIVE)
    public void dailyRememberAtEightFortyFive() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:45")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8:45");
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT_FIFTY_FIVE)
    public void dailyRememberAtEightFiftyFive() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:55")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8:55");
        }
    }
}

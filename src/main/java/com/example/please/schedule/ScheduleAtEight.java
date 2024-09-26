package com.example.please.schedule;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.NotificationTime;
import com.example.please.constant.Phrases;
import com.example.please.handler.MessageChecker;
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
public class ScheduleAtEight implements ScheduleDailyMessage{

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT)
    @Override
    public void dailyRemember() {
        List<User> users = userService.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);

            if (MessageChecker.isNotificationAt(user, notification, "8:00")) {
                new TelegramBot(config, userService, notificationService).sendMessage(user.getChatId(), Phrases.REMEMBER);
            }

            log.info(user.getFullName() + " got the notification at 8 am");
        }
    }
}

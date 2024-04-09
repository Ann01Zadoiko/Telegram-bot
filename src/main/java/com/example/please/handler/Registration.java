package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.constant.Phrases;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class Registration {

    private final UserService userService;
    private final NotificationService notificationService;


    //register a new user
    @SneakyThrows
    public void registerUser(Message message, TelegramBot bot) {
        if (!userService.existsByChatId(message.getChatId())) {
            User user = new User();
            user.setChatId(message.getChatId());
            user.setDateOfBirth(LocalDate.parse("1900-01-01"));
            userService.save(user);

            Notification notification = new Notification();
            notification.setTurnOn(true);
            notification.setTimeOfNotification("0 0 9 * * MON-FRI");
            notification.setUser(user);
            notificationService.save(notification);

            bot.sendMessage(message.getChatId(), Phrases.START);
            log.info("User (" + user.getChatId() + ") is registered");
        } else {
            bot.sendMessage(message.getChatId(), Phrases.START_OLD_USER);
            log.info("User in the db");
        }
    }
}

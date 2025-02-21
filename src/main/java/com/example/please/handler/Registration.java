package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.constant.EnumNotification;
import com.example.please.constant.Phrases;
import com.example.please.constant.Steps;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@RequiredArgsConstructor
public class Registration {

    private final UserService userService;
    private final NotificationService notificationService;

    //register a new user
    @SneakyThrows
    public void start(Message message, TelegramBot bot) {

        if (!userService.existsByChatId(message.getChatId())) {
            User user = new User();
            user.setChatId(message.getChatId());
            user.setAtWork((byte) 0);
            user.setStatusEnum(StatusEnum.NOTHING);
            userService.save(user);

            Notification notification = new Notification();
            notification.setTurnOn(true);
            notification.setTimeOfNotification(EnumNotification.EIGHT_FIFTY_FIVE.getTime());
            notification.setUser(user);

            log.info("Notification before saving: {}", notification);

            notificationService.save(notification);

            bot.sendMessageRegistration(message.getChatId(), Steps.START);
            log.info("User ({}) is registered", user.getChatId());

        } else {
            bot.sendMessage(message.getChatId(), Phrases.START_OLD_USER);
            log.info("User in the db");
        }
    }
}

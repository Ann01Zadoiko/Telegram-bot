package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.Phrases;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
public class Registration {

    private final UserService userService;
    private final NotificationService notificationService;

    @SneakyThrows
    public void registerUser(Message message) {
        if (!userService.existsByChatId(message.getChatId())) {
            User user = new User();
            user.setChatId(message.getChatId());
            user.setFullName("Хтось");
            user.setStatus(Status.WORK);
            userService.save(user);

            Notification notification = new Notification();
            notification.setTurnOn(true);
            notification.setTimeOfNotification("0 0 9 * * MON-FRI");
            notification.setUser(user);
            notificationService.save(notification);

            new TelegramBot(new BotConfig(), userService, notificationService).sendMessage(message.getChatId(), Phrases.START_NEW_USER);
        } else {
            new TelegramBot(new BotConfig(), userService, notificationService).sendMessage(message.getChatId(), Phrases.START_OLD_USER);
        }
    }
}

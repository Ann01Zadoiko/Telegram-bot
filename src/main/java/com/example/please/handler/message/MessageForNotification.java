package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.*;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
@RequiredArgsConstructor
public class MessageForNotification {

    private final NotificationService notificationService;

    //make switch case
    @SneakyThrows
    public void getNotification(String messageText, User user, long chatId, TelegramBot bot){
        Notification notification = notificationService.getNotificationByUser(user);

        EnumSet<EnumNotification> notifications = EnumSet.allOf(EnumNotification.class);
        notifications.remove(EnumNotification.BACK);

        if (messageText.equals(Commands.NOTIFICATION) ){

            if (!notification.getTurnOn()){
                notifications.remove(EnumNotification.TURN_OFF);
                bot.executeSetting(chatId, new SettingsButton().getButtonsDifferentCount(notifications), Phrases.NOTIFICATION + "вимкнено");
            }

            if (notification.getTurnOn()){
                for (EnumNotification enumNotification: EnumNotification.values()){
                    if (notification.getTimeOfNotification().equals(enumNotification.getTime())){
                        notifications.remove(enumNotification);
                        bot.executeSetting( chatId, new SettingsButton().getButtonsDifferentCount(notifications), Phrases.NOTIFICATION + enumNotification.getTime());
                    }
                }
            }
        }
    }
}

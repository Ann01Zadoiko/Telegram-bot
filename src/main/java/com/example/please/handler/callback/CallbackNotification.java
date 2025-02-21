package com.example.please.handler.callback;

import com.example.please.bot.TelegramBot;
import com.example.please.constant.EnumNotification;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CallbackNotification {

    private final NotificationService notificationService;

    public void commandsForCallbackOfNotifications(String data, Notification notification, long chatId, long messageId, TelegramBot bot){

        for (EnumNotification enumNotification: EnumNotification.values()){

            if (data.equals(EnumNotification.TURN_OFF.getTime())){
                notification.setTurnOn(false);
                notificationService.save(notification);
                bot.executeEditMessage("Ви вимкнули нагадування", chatId, messageId);
            }

            if (data.equals(enumNotification.getTime())
                    && !(data.equals(EnumNotification.TURN_OFF.getTime()))
                    && !(data.equals(EnumNotification.BACK.getTime()))){
                notification.setTurnOn(true);
                notification.setTimeOfNotification(enumNotification.getTime());
                notificationService.save(notification);
                bot.executeEditMessage("Ви змінили своє нагадування о " + notification.getTimeOfNotification(), chatId, messageId);
            }
            log.info("User ({}) change the notification at {}", chatId, data);
        }
    }
}

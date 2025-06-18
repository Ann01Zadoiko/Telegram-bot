package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.EnumNotification;
import com.telegram.notification.Notification;
import com.telegram.notification.NotificationService;
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
                String text = "Ви змінили своє нагадування о " + notification.getTimeOfNotification();
                bot.executeEditMessage(text, chatId, messageId);
            }
            log.info("User ({}) change the notification at {}", chatId, data);
        }
    }
}

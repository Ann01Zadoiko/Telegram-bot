package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.Commands;
import com.telegram.constant.EnumNotification;
import com.telegram.constant.Phrases;
import com.telegram.notification.Notification;
import com.telegram.notification.NotificationService;
import com.telegram.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class MessageForNotification {

    private final NotificationService notificationService;

    //make switch case
    @SneakyThrows
    public void getNotification(String messageText, User user, long chatId, TelegramBot bot){
        Notification notification = notificationService.getNotificationByUser(user);

        Set<EnumNotification> notifications = EnumSet.allOf(EnumNotification.class);
        notifications.remove(EnumNotification.BACK);
        InlineKeyboardSettingsButton<EnumNotification> button = new InlineKeyboardSettingsButton<>();

        if (messageText.equals(Commands.NOTIFICATION) ){

            if (!notification.getTurnOn()){
                notifications.remove(EnumNotification.TURN_OFF);
                bot.executeSetting(chatId, button.getButtonsDifferentCount(notifications), Phrases.NOTIFICATION + "вимкнено");
            }

            if (notification.getTurnOn()){
                for (EnumNotification enumNotification: EnumNotification.values()){
                    if (notification.getTimeOfNotification().equals(enumNotification.getTime())){
                        notifications.remove(enumNotification);
                        bot.executeSetting(chatId, button.getButtonsDifferentCount(notifications), Phrases.NOTIFICATION + enumNotification.getTime());
                    }
                }
            }
        }
    }
}

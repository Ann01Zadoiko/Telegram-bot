package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Callback;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MessageForNotification {

    private final NotificationService notificationService;


    //make switch case
    @SneakyThrows
    public void getNotification(String messageText, User user, long chatId, TelegramBot bot){
        Notification notification = notificationService.getNotificationByUser(user);

        List<String> list = new ArrayList<>();
        list.add(Callback.EIGHT);
        list.add(Callback.EIGHT_FORTY);
        list.add(Callback.EIGHT_FORTY_FIVE);
        list.add(Callback.EIGHT_FIFTY);
        list.add(Callback.EIGHT_FIFTY_FIVE);
        list.add(Callback.TURN_OFF);

        if (messageText.equals(Commands.NOTIFICATION)){

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:00")){
                log.info("User (" + chatId + ") with notification (8)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT);
                bot.executeSetting(chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "у Вас о 8 ранку");
            }

            if (!notification.getTurnOn()){
                log.info("User (" + chatId + ") with notification which off");

                List<String> listTime = list;
                listTime.remove(Callback.TURN_OFF);
                bot.executeSetting(chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "вимкнено");
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:40")){
                log.info("User (" + chatId + ") with notification (8 40)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FORTY);
                bot.executeSetting(chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "у Вас о 8:40");
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:45")){
                log.info("User (" + chatId + ") with notification (8 45)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FORTY_FIVE);
                bot.executeSetting(chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "у Вас о 8:45");
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:50")){
                log.info("User (" + chatId + ") with notification (8 50)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FIFTY);
                bot.executeSetting(chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "у Вас о 8:50");
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:55")){
                log.info("User (" + chatId + ") with notification (8 55)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FIFTY_FIVE);
                bot.executeSetting( chatId, SettingsButton.getButtonsDifferentCount(listTime), Phrases.NOTIFICATION + "у Вас о 8:55");
            }
        }
    }
}

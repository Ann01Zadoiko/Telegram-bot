package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.*;
import com.example.please.config.BotConfig;
import com.example.please.constant.*;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class BotHandlerCallback {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    public void commandsForCallbackOfNotifications(String data, Notification notification, long chatId, long messageId, TelegramBot bot){
        switch (data) {

            case Callback.EIGHT -> {
                notification.setTimeOfNotification("8:00");
                notification.setTurnOn(true);
                notificationService.save(notification);
                log.info("User (" + chatId + ") change the notification at 8 am");
                bot.executeEditMessage("Ви змінили своє нагадування о 8 ранку", chatId, messageId);
            }

            case Callback.EIGHT_FORTY -> {
                notification.setTimeOfNotification("8:40");
                notification.setTurnOn(true);
                notificationService.save(notification);
                log.info("User (" + chatId + ") change the notification at 8:40");
                bot.executeEditMessage("Ви змінили своє нагадування о 8:40", chatId, messageId);
            }

            case Callback.EIGHT_FORTY_FIVE -> {
                notification.setTimeOfNotification("8:45");
                notification.setTurnOn(true);
                notificationService.save(notification);
                log.info("User (" + chatId + ") change the notification at 8:45");
                bot.executeEditMessage("Ви змінили своє нагадування о 8:45", chatId, messageId);
            }

            case Callback.EIGHT_FIFTY -> {
                notification.setTimeOfNotification("8:50");
                notification.setTurnOn(true);
                notificationService.save(notification);
                log.info("User (" + chatId + ") change the notification at 8:50");
                bot.executeEditMessage("Ви змінили своє нагадування о 8:50", chatId, messageId);
            }

            case Callback.EIGHT_FIFTY_FIVE -> {
                notification.setTimeOfNotification("8:55");
                notification.setTurnOn(true);
                notificationService.save(notification);
                log.info("User (" + chatId + ") change the notification at 8:55");
                bot.executeEditMessage("Ви змінили своє нагадування о 8:55", chatId, messageId);
            }

            case Callback.TURN_OFF -> {
                notification.setTurnOn(false);
                notificationService.save(notification);
                log.info("User (" + chatId + ") turned off the notification");
                bot.executeEditMessage("Ви вимкнули нагадування", chatId, messageId);
            }
        }
    }

    @SneakyThrows
    public void commandsForStatuses(String data, User user, long chatId, long messageId, TelegramBot bot){
        switch (data) {

            case Callback.WORK -> {
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(StatusEnum.WORK);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                } else {
                    user.setStatusEnum(StatusEnum.WORK);
                    userService.save(user);
                    bot.executeEditMessageTextWithButton( Phrases.STATUS + "працюєте", chatId, messageId, BackButton.getBackToSettings());
                }
                log.info("User (" + chatId + ") change the status to WORK");
            }

            case Callback.SICK -> {
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(StatusEnum.SICK);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                } else {
                    user.setStatusEnum(StatusEnum.SICK);
                    userService.save(user);
                    bot.executeEditMessageTextWithButton( Phrases.STATUS + "на лікарняному" , chatId, messageId, BackButton.getBackToSettings());
                }
                log.info("User (" + chatId + ") change the status to SICK");
            }

            case Callback.VACATION -> {
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(StatusEnum.VACATION);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                } else {
                    user.setStatusEnum(StatusEnum.VACATION);
                    userService.save(user);
                    bot.executeEditMessageTextWithButton( Phrases.STATUS + "у відпустці", chatId, messageId, BackButton.getBackToSettings());
                }
                log.info("User (" + chatId + ") change the status to VACATION");
            }

            case Callback.BUSINESS_TRIP -> {
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(StatusEnum.BUSINESS_TRIP);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                } else {
                    user.setStatusEnum(StatusEnum.BUSINESS_TRIP);
                    userService.save(user);

                    bot.executeEditMessageTextWithButton( Phrases.STATUS + "у відрядженні", chatId, messageId, BackButton.getBackToSettings());
                }
                log.info("User (" + chatId + ") change the status to BUSINESS TRIP");
            }

            case Callback.REMOTE -> {
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(StatusEnum.REMOTE);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                } else {
                    user.setStatusEnum(StatusEnum.REMOTE);
                    userService.save(user);

                    bot.executeEditMessageTextWithButton( Phrases.STATUS + "на дистанційній роботі", chatId, messageId, BackButton.getBackToSettings());
                }
                log.info("User (" + chatId + ") change the status to REMOTE");
            }
        }
    }

    //show user's notification and ability to change to another one
    public void getNotification(String data, Notification notification, long chatId, long messageId, TelegramBot bot){

        List<String> list = new ArrayList<>();
        list.add(Callback.EIGHT);
        list.add(Callback.EIGHT_FORTY);
        list.add(Callback.EIGHT_FORTY_FIVE);
        list.add(Callback.EIGHT_FIFTY);
        list.add(Callback.EIGHT_FIFTY_FIVE);
        list.add(Callback.TURN_OFF);
        list.add(Callback.BACK);

        if (data.equals(Commands.NOTIFICATION)){

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){
                log.info("User (" + chatId + ") with notification (8)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }

            if (!notification.getTurnOn()){
                log.info("User (" + chatId + ") with notification which off");

                List<String> listTime = list;
                listTime.remove(Callback.TURN_OFF);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:40")){
                log.info("User (" + chatId + ") with notification (8 40)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FORTY);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:40", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:45")){
                log.info("User (" + chatId + ") with notification (8 45)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FORTY_FIVE);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:45", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:50")){
                log.info("User (" + chatId + ") with notification (8 50)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FIFTY);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:50", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8:55")){
                log.info("User (" + chatId + ") with notification (8 55)");

                List<String> listTime = list;
                listTime.remove(Callback.EIGHT_FIFTY_FIVE);
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:55", chatId, messageId, SettingsButton.getButtonsDifferentCount(listTime));
            }
        }
    }

    //show user's full name
    public void getFullName(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.FULL_NAME)){
            String date = "Ваш ПІБ: " + user.getFullName();
            log.info("Show to user his full name: " + user.getFullName());

            bot.executeEditMessageTextWithButton(date, chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //show user's status and ability to change to another one
    public void getStatus(String data,User user, long chatId, long messageId, TelegramBot bot){

        List<String> list = new ArrayList<>();
        list.add(Callback.WORK);
        list.add(Callback.REMOTE);
        list.add(Callback.SICK);
        list.add(Callback.VACATION);
        list.add(Callback.BUSINESS_TRIP);
        list.add(Callback.BACK);


        if (data.equals(Settings.STATUS)){

            if (user.getStatusEnum().equals(StatusEnum.WORK)){
                List<String> listWork = list;
                listWork.remove(Callback.WORK);
                bot.executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, SettingsButton.getButtonsDifferentCount(listWork));
            }

            if (user.getStatusEnum().equals(StatusEnum.SICK)){
                List<String> listWork = list;
                listWork.remove(Callback.SICK);
                bot.executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, SettingsButton.getButtonsDifferentCount(listWork));
            }

            if (user.getStatusEnum().equals(StatusEnum.VACATION)){
                List<String> listWork = list;
                listWork.remove(Callback.VACATION);
                bot.executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, SettingsButton.getButtonsDifferentCount(listWork));
            }

            if (user.getStatusEnum().equals(StatusEnum.BUSINESS_TRIP)){
                List<String> listWork = list;
                listWork.remove(Callback.BUSINESS_TRIP);
                bot.executeEditMessageTextWithButton("На даний момент Ви у відрядженні", chatId, messageId, SettingsButton.getButtonsDifferentCount(listWork));
            }

            if (user.getStatusEnum().equals(StatusEnum.REMOTE)){
                List<String> listWork = list;
                listWork.remove(Callback.REMOTE);
                bot.executeEditMessageTextWithButton("На даний момент Ви на дистанційній роботі", chatId, messageId, SettingsButton.getButtonsDifferentCount(listWork));
            }

            log.info("Show the status for " + user.getFullName());
        }
    }

    //show user's phone number
    public void getPhoneNumber(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.PHONE_NUMBER)){

            if (user.getPhoneNumber() == null) {
                log.info(user.getFullName() + " doesn't have a phone number");
                bot.executeEditMessageTextWithButton(Phrases.PHONE_NUMBER, chatId, messageId, BackButton.getBackToSettings());
            } else {
                log.info("Show the phone number for " + user.getFullName());
                bot.executeEditMessageTextWithButton("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    //turn back to settings
    public void getBackToSettings(String data, long chatId, long messageId, TelegramBot bot){

        List<String> list = new ArrayList<>();
        list.add(Settings.FULL_NAME);
        list.add(Settings.PHONE_NUMBER);
        list.add(Settings.STATUS);

        if (data.equals(Callback.BACK)){
            log.info("User (" + chatId + ") pressed button to back at the settings");

            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, SettingsButton.getButtonsDifferentCount(list));
        }
    }
}

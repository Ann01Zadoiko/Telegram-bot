package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.*;
import com.example.please.config.BotConfig;
import com.example.please.constant.Callback;
import com.example.please.constant.Phrases;
import com.example.please.constant.Settings;
import com.example.please.constant.Steps;
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

@Slf4j
@Builder
@RequiredArgsConstructor
public class BotHandlerCallback {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    //the notification was changed at 9 am
    public void getNine(Notification notification, long chatId, long messageId, String data, TelegramBot bot){

        if (data.equals(Callback.NINE)) {
            notification.setTimeOfNotification("0 0 9 * * MON-FRI");
            notification.setTurnOn(true);
            notificationService.save(notification);

            log.info("User (" + chatId + ") change the notification at 9 am");

            bot.executeEditMessageTextWithButton("Ви змінили своє нагадування о 9 ранку", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the notification was changed at 8 am
    public void getEight(String data, Notification notification, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.EIGHT)) {
            notification.setTimeOfNotification("0 0 8 * * MON-FRI");
            notification.setTurnOn(true);
            notificationService.save(notification);

            log.info("User (" + chatId + ") change the notification at 8 am");

            bot.executeEditMessageTextWithButton("Ви змінили своє нагадування о 8 ранку", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the notification was turned off
    public void getOff(String data, Notification notification, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.TURN_OFF)) {
            notification.setTurnOn(false);
            notificationService.save(notification);

            log.info("User (" + chatId + ") turned off the notification");

            bot.executeEditMessageTextWithButton("Ви вимкнули нагадування", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the status was changed to WORK
    @SneakyThrows
    public void getWork(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.WORK)) {

            if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                user.setStatusEnum(StatusEnum.WORK);
                userService.save(user);

                bot.execute(EditMessageText
                        .builder()
                        .chatId(chatId)
                        .messageId((int) messageId)
                        .text(Steps.THE_END)
                        .build());

                bot.sendMessage(chatId, Steps.STEP_4);

            } else {

                user.setStatusEnum(StatusEnum.WORK);
                userService.save(user);

                bot.executeEditMessageTextWithButton( Phrases.STATUS + "працюєте", chatId, messageId, BackButton.getBackToSettings());
            }

            log.info("User (" + chatId + ") change the status to WORK");
        }
    }

    //the status was changed to SICK
    @SneakyThrows
    public void getSick(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.SICK)) {

            if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                user.setStatusEnum(StatusEnum.SICK);
                userService.save(user);

                bot.execute(EditMessageText
                        .builder()
                        .chatId(chatId)
                        .messageId((int) messageId)
                        .text(Steps.THE_END)
                        .build());

                bot.sendMessage(chatId, Steps.STEP_4);

            } else {
                user.setStatusEnum(StatusEnum.SICK);
                userService.save(user);

                bot.executeEditMessageTextWithButton( Phrases.STATUS + "на лікарняному" , chatId, messageId, BackButton.getBackToSettings());
            }

            log.info("User (" + chatId + ") change the status to SICK");
        }
    }

    //the status was changed VACATION
    @SneakyThrows
    public void getVacation(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.VACATION)) {

            if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                user.setStatusEnum(StatusEnum.VACATION);
                userService.save(user);

                bot.execute(EditMessageText
                        .builder()
                        .chatId(chatId)
                        .messageId((int) messageId)
                        .text(Steps.THE_END)
                        .build());

                bot.sendMessage(chatId, Steps.STEP_4);

            } else {
                user.setStatusEnum(StatusEnum.VACATION);
                userService.save(user);

                bot.executeEditMessageTextWithButton( Phrases.STATUS + "у відпустці", chatId, messageId, BackButton.getBackToSettings());
            }

            log.info("User (" + chatId + ") change the status to VACATION");
        }
    }

    @SneakyThrows
    public void getBusinessTrip(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Callback.BUSINESS_TRIP)) {

            if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                user.setStatusEnum(StatusEnum.BUSINESS_TRIP);
                userService.save(user);

                bot.execute(EditMessageText
                        .builder()
                        .chatId(chatId)
                        .messageId((int) messageId)
                        .text(Steps.THE_END)
                        .build());

                bot.sendMessage(chatId, Steps.STEP_4);

            } else {
                user.setStatusEnum(StatusEnum.BUSINESS_TRIP);
                userService.save(user);

                bot.executeEditMessageTextWithButton( Phrases.STATUS + "у відрядженні", chatId, messageId, BackButton.getBackToSettings());
            }

            log.info("User (" + chatId + ") change the status to BUSINESS TRIP");
        }
    }

    //show user's notification and ability to change to another one
    public void getNotification(String data, Notification notification, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.NOTIFICATION)){

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("9")){
                log.info("User (" + chatId + ") with notification (9)");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 9 ранку", chatId, messageId, SettingsButton.getButtons(Callback.EIGHT, Callback.TURN_OFF, Callback.BACK));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){
                log.info("User (" + chatId + ") with notification (8)");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.TURN_OFF, Callback.BACK));
            }

            if (!notification.getTurnOn()){
                log.info("User (" + chatId + ") with notification which off");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.EIGHT, Callback.BACK));
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

    //show user's password
    public void getPassword(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.PASSWORD)){

            if (user.getPassword() == null) {
                log.info(user.getFullName() + " doesn't have a password");
                bot.executeEditMessageTextWithButton("У Вас немає паролю!\nНавіщо тоді натискати цю кнопку?", chatId, messageId, BackButton.getBackToSettings());
            } else {
                log.info("Show the password for " + user.getFullName());
                bot.executeEditMessageTextWithButton( user.getPassword(), chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    //show user's room
    public void getRoom(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.ROOM)){

            if (user.getRoom() == null) {
                log.info(user.getFullName() + " doesn't have a room");
                bot.executeEditMessageTextWithButton(Phrases.ROOM, chatId, messageId, BackButton.getBackToSettings());
            } else {
                log.info("Show the room for " + user.getFullName());
                bot.executeEditMessageTextWithButton("Ви працюєте в " + user.getRoom() + " кабінеті", chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    //show user's status and ability to change to another one
    public void getStatus(String data,User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.STATUS)){

            if (user.getStatusEnum().equals(StatusEnum.WORK)){
                bot.executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, SettingsButton.getButtonsStatus(Callback.SICK, Callback.VACATION, Callback.BUSINESS_TRIP, Callback.BACK));
            }

            if (user.getStatusEnum().equals(StatusEnum.SICK)){
                bot.executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, SettingsButton.getButtonsStatus(Callback.WORK, Callback.VACATION, Callback.BUSINESS_TRIP, Callback.BACK));
            }

            if (user.getStatusEnum().equals(StatusEnum.VACATION)){
                bot.executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, SettingsButton.getButtonsStatus(Callback.WORK, Callback.SICK, Callback.BUSINESS_TRIP, Callback.BACK));
            }

            if (user.getStatusEnum().equals(StatusEnum.BUSINESS_TRIP)){
                bot.executeEditMessageTextWithButton("На даний момент Ви у відрядженні", chatId, messageId, SettingsButton.getButtonsStatus(Callback.WORK, Callback.SICK, Callback.VACATION, Callback.BACK));
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

        if (data.equals(Callback.BACK)){
            log.info("User (" + chatId + ") pressed button to back at the settings");
            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, SettingsButton.inlineButtonsForSettings());
        }
    }
}

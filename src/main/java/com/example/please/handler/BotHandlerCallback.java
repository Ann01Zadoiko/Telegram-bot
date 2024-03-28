package com.example.please.handler;

import com.example.please.atWork.ListOfEmployees;
import com.example.please.bot.TelegramBot;
import com.example.please.buttons.*;
import com.example.please.config.BotConfig;
import com.example.please.constant.Callback;
import com.example.please.constant.Phrases;
import com.example.please.constant.Settings;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
            bot.executeEditMessageTextWithButton("Ви вимкнути нагадування", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the status was changed to WORK
    public void getWork(String data, User user, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.WORK)) {
            user.setStatus(Status.WORK);
            userService.save(user);

            log.info("User (" + chatId + ") change the status to WORK");
            bot.executeEditMessageTextWithButton( Phrases.STATUS + "працюєте", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the status was changed to SICK
    public void getSick(String data, User user, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.SICK)) {
            user.setStatus(Status.SICK);
            userService.save(user);

            log.info("User (" + chatId + ") change the status to SICK");
            bot.executeEditMessageTextWithButton( Phrases.STATUS + "на лікарняному" , chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //the status was changed VACATION
    public void getVacation(String data, User user, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.VACATION)) {
            user.setStatus(Status.VACATION);
            userService.save(user);

            log.info("User (" + chatId + ") change the status to VACATION");
            bot.executeEditMessageTextWithButton( Phrases.STATUS + "у відпустці", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    //show user's notification and ability to change to another one
    public void getNotification(String data, Notification notification, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Settings.NOTIFICATION)){
            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("9")){
                log.info("User (" + chatId + ") with notification (9)");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 9 ранку", chatId, messageId, SettingsButton.getButtons(Callback.EIGHT, Callback.TURN_OFF));
            }
            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){
                log.info("User (" + chatId + ") with notification (8)");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.TURN_OFF));
            }
            if (!notification.getTurnOn()){
                log.info("User (" + chatId + ") with notification which off");
                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.EIGHT));
            }
        }
    }

    ///show user's full name
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
            if (user.getStatus().equals(Status.WORK)){
                bot.executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, SettingsButton.getButtons(Callback.SICK, Callback.VACATION));
            }
            if (user.getStatus().equals(Status.SICK)){
                bot.executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, SettingsButton.getButtons(Callback.WORK, Callback.VACATION));
            }
            if (user.getStatus().equals(Status.VACATION)){
                bot.executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, SettingsButton.getButtons(Callback.WORK, Callback.SICK));
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

    //show list of users who work
    @SneakyThrows
    public void getListOfWork(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.LIST_OF_WORK)){
            log.info("Show the list of users who at work for " + chatId);
            bot.executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployeeWork(), chatId, messageId, BackButton.getBackToList());
        }
    }

    //show list of users who sick
    @SneakyThrows
    public void getListOfSick(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.LIST_OF_SICK)){
            log.info("Show the list of users who sick for " + chatId);
            bot.executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployees(Status.SICK), chatId,messageId, BackButton.getBackToList());
        }
    }

    //show list of users who in vacation
    @SneakyThrows
    public void getListOfVacation(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.LIST_OF_VACATION)){
            log.info("Show the list of user who in vacation for " + chatId);
            bot.executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployees(Status.VACATION), chatId, messageId, BackButton.getBackToList());
        }
    }

    //turn back to settings
    public void getBackToSettings(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Callback.BACK_TO_SETTINGS)){
            log.info("User (" + chatId + ") pressed button to back at the settings");
            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, SettingsButton.inlineButtonsForSettings());
        }
    }

    //turn back to list (work, sick, vacation)
    public void getBackToList(String data, long charId, long messageId, TelegramBot bot){
        if (data.equals(Callback.BACK_TO_LIST)){
            log.info("User (" + charId + ") pressed button to back at the lists");
            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, charId,messageId, ListButton.getButtonsList());
        }
    }
}

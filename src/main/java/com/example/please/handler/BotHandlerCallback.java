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
import org.aspectj.weaver.ast.Not;


@Builder
@RequiredArgsConstructor
public class BotHandlerCallback {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    public void getNine(Notification notification, long chatId, long messageId, String data){
        if (data.equals(Callback.NINE)) {

            notification.setTimeOfNotification("0 0 9 * * MON-FRI");
            notification.setTurnOn(true);
            notificationService.save(notification);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton("Ви змінили своє нагадування о 9 ранку", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getEight(String data, Notification notification, long chatId, long messageId){
        if (data.equals(Callback.EIGHT)) {

            notification.setTimeOfNotification("0 0 8 * * MON-FRI");
            notification.setTurnOn(true);
            notificationService.save(notification);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton("Ви змінили своє нагадування о 8 ранку", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getOff(String data, Notification notification, long chatId, long messageId){
        if (data.equals(Callback.TURN_OFF)) {

            notification.setTurnOn(false);
            notificationService.save(notification);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton("Ви вимкнути нагадування", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getWork(String data, User user, long chatId, long messageId){
        if (data.equals(Callback.WORK)) {

            user.setStatus(Status.WORK);
            userService.save(user);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton( Phrases.STATUS + "працюєте", chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getSick(String data, User user, long chatId, long messageId){
        if (data.equals(Callback.SICK)) {

            user.setStatus(Status.SICK);
            userService.save(user);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton( Phrases.STATUS + "на лікарняному" , chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getVacation(String data, User user, long chatId, long messageId){
        if (data.equals(Callback.VACATION)) {

            user.setStatus(Status.VACATION);
            userService.save(user);

            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton( Phrases.STATUS + "у відпустці", chatId, messageId, BackButton.getBackToSettings());

        }
    }

    public void getNotification(String data, Notification notification, long chatId, long messageId){
        if (data.equals(Settings.NOTIFICATION)){

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("9")){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 9 ранку", chatId, messageId, SettingsButton.getButtons(Callback.EIGHT, Callback.TURN_OFF));
            }

            if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.TURN_OFF));
            }

            if (!notification.getTurnOn()){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, SettingsButton.getButtons(Callback.NINE, Callback.EIGHT));
            }
        }
    }

    public void getFullName(String data, User user, long chatId, long messageId){
        if (data.equals(Settings.FULL_NAME)){
            String date = "Ваш ПІБ: " + user.getFullName();
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(date, chatId, messageId, BackButton.getBackToSettings());
        }
    }

    public void getPassword(String data, User user, long chatId, long messageId){
        if (data.equals(Settings.PASSWORD)){
            if (user.getPassword() == null) {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("У Вас немає паролю!\nНавіщо тоді натискати цю кнопку?", chatId, messageId, BackButton.getBackToSettings());
            } else {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton( user.getPassword(), chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    public void getRoom(String data, User user, long chatId, long messageId){
        if (data.equals(Settings.ROOM)){
            if (user.getRoom() == null) {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton(Phrases.ROOM, chatId, messageId, BackButton.getBackToSettings());
            } else {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("Ви працюєте в " + user.getRoom() + " кабінеті", chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    public void getStatus(String data,User user, long chatId, long messageId){
        if (data.equals(Settings.STATUS)){
            if (user.getStatus().equals(Status.WORK)){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, SettingsButton.getButtons(Callback.SICK, Callback.VACATION));

            }

            if (user.getStatus().equals(Status.SICK)){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, SettingsButton.getButtons(Callback.WORK, Callback.VACATION));
            }

            if (user.getStatus().equals(Status.VACATION)){
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, SettingsButton.getButtons(Callback.WORK, Callback.SICK));
            }
        }
    }

    public void getPhoneNumber(String data, User user, long chatId, long messageId){
        if (data.equals(Settings.PHONE_NUMBER)){
            if (user.getPhoneNumber() == null) {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton(Phrases.PHONE_NUMBER, chatId, messageId, BackButton.getBackToSettings());
            } else {
                new TelegramBot(config, userService, notificationService)
                        .executeEditMessageTextWithButton("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId, BackButton.getBackToSettings());
            }
        }
    }

    @SneakyThrows
    public void getListOfWork(String data, long chatId, long messageId){
        if (data.equals(Callback.LIST_OF_WORK)){
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployeeWork(), chatId, messageId, BackButton.getBackToList());
        }
    }

    @SneakyThrows
    public void getListOfSick(String data, long chatId, long messageId){
        if (data.equals(Callback.LIST_OF_SICK)){
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployees(Status.SICK), chatId,messageId, BackButton.getBackToList());
        }
    }

    @SneakyThrows
    public void getListOfVacation(String data, long chatId, long messageId){
        if (data.equals(Callback.LIST_OF_VACATION)){
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(new ListOfEmployees(userService).printEmployees(Status.VACATION), chatId, messageId, BackButton.getBackToList());
        }
    }

    public void getBackToSettings(String data, long chatId, long messageId){
        if (data.equals(Callback.BACK_TO_SETTINGS)){
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, SettingsButton.inlineButtonsForSettings());
        }
    }

    public void getBackToList(String data, long charId, long messageId){
        if (data.equals(Callback.BACK_TO_LIST)){
            new TelegramBot(config, userService, notificationService)
                    .executeEditMessageTextWithButton(Phrases.CHOOSE, charId,messageId, ListButton.getButtonsIfTurnOnAtNine());
        }
    }
}

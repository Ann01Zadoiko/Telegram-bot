package com.example.please.handler;

import com.example.please.atWork.AtWorkService;
import com.example.please.atWork.ListOfEmployees;
import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.config.BotConfig;
import com.example.please.constant.Callback;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
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
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class BotHandlerMessage {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    //user start to use the bot
    public void getStart(Update update, String messageText, TelegramBot bot){

        if (messageText.equals(Commands.START_PRIVATE)) {
            new Registration(userService, notificationService).start(update.getMessage(), bot);
        }
    }

    //user enter or change his full name
    @SneakyThrows
    public void getFullName(User user, String messageText, long charId, String [] stringBuilder, TelegramBot bot){

        if (MessageChecker.isFullName(stringBuilder, messageText)) {

            if (user.getFullName() == null){
                user.setFullName(messageText);
                userService.save(user);
                bot.sendMessageRegistration(charId, Steps.STEP_1);
            } else {
                user.setFullName(messageText);
                userService.save(user);
                bot.sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
            }

            log.info("User set new full name: " + user.getFullName());
        }
    }

    //user is added to the list (work)
    @SneakyThrows
    public void getAtWork(User user, long charId, String messageText, TelegramBot bot){

        if (messageText.equals(Commands.AT_WORK)) {
            String s = new AtWorkService(userService).addUserToTheList(user, LocalTime.now());

            if(user.getStatusEnum().equals(StatusEnum.WORK) || user.getStatusEnum().equals(StatusEnum.REMOTE)){
                log.info(user.getFullName() + " is at work");
                bot.sendMessage(charId, s);
            } else {
                bot.sendMessage(charId, "Ти не маєшь права натискати на цю кнопку!");
            }
        }
    }

    //user enter or change his phone number
    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId, TelegramBot bot){
        List<String> list = new ArrayList<>();
        list.add(Callback.WORK);
        list.add(Callback.REMOTE);
        list.add(Callback.SICK);
        list.add(Callback.VACATION);
        list.add(Callback.BUSINESS_TRIP);

        if (MessageChecker.isPhoneNumber(messageText)) {

            if (user.getPhoneNumber() == null){
                user.setPhoneNumber(messageText);
                userService.save(user);

                bot.executeSetting(charId, SettingsButton.getButtonsDifferentCount(list), Steps.STEP_2);
            } else {
                user.setPhoneNumber(messageText);
                userService.save(user);
                bot.sendMessage(charId, Phrases.PHONE_NUMBER_INFO + user.getPhoneNumber());
            }

            log.info(user.getFullName() + " set a new phone number");
        }
    }

    //user send message for everyone
    @SneakyThrows
    public void getSend(String messageText, User user, TelegramBot bot){

        if (messageText.contains(Commands.SEND)){
            String message = messageText.substring(6);

            for(User user1: userService.listAll()){
                bot.sendMessage(user1.getChatId(), message + "\nВід: " + user.getFullName());
            }

            log.info(user.getFullName() + " send message for everyone");
        }
    }

    //show unexpected message
    @SneakyThrows
    public void getUnexpectedMessage(String messageText, String [] stringBuilder, long charId, TelegramBot bot){

        if (MessageChecker.isUnexpectedMessage(messageText, stringBuilder)) {
            bot.sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            log.info("User (" + charId + ") entered incorrect message");
        }
    }

    //press the button (list of users)
    @SneakyThrows
    public void getListOfEmployees(String messageText, long charId, TelegramBot bot){

        if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
            bot.sendMessage(charId, new ListOfEmployees(userService).printAllUsers());
            log.info("User (" + charId + ") pressed  the button (list of employees)");
        }
    }

    //press the button (the settings)
    @SneakyThrows
    public void getSettings(String messageText, long charId, TelegramBot bot){

        if (messageText.equals(Commands.SETTINGS)){
            bot.executeSetting(charId, SettingsButton.inlineButtonsForSettings(), Phrases.CHOOSE);
            log.info("User (" + charId + ") pressed the button (the settings)");
        }
    }

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

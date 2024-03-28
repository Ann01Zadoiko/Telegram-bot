package com.example.please.handler;

import com.example.please.atWork.AtWorkService;
import com.example.please.bot.TelegramBot;
import com.example.please.buttons.ListButton;
import com.example.please.buttons.SettingsButton;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;

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
            new Registration(userService, notificationService).registerUser(update.getMessage(), bot);
        }
    }

    //user enter or change his full name
    @SneakyThrows
    public void getFullName(User user, String messageText, long charId, String [] stringBuilder, TelegramBot bot){
        if (MessageChecker.isFullName(stringBuilder, messageText)) {
            if (user.getFullName().equals("Хтось")) {
                user.setFullName(messageText);
                userService.update(user);

                bot.sendMessage(charId, Phrases.FULL_NAME);
            } else {
                user.setFullName(messageText);
                userService.update(user);

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
            if(user.getStatus().equals(Status.WORK)){
                log.info(user.getFullName() + " is at work");
                bot.sendMessage(charId, s);
            } else {
                bot.sendMessage(charId, "Ти не маєшь права натискати на цю кнопку!");
            }
        }
    }


    //user enter or change his password
    @SneakyThrows
    public void getPassword(User user, String messageText, long charId, String [] stringBuilder, TelegramBot bot){
        if (MessageChecker.isPassword(stringBuilder, messageText)) {
            String password = Converter.convertPassword(messageText);

            user.setPassword(password);
            userService.save(user);

            bot.sendMessage(charId, password);
            log.info(user.getFullName() + " set a new password");
        }
    }

    //user enter or change his room
    @SneakyThrows
    public void getRoom(User user, String messageText, long charId, TelegramBot bot){

        if (MessageChecker.isARoom(messageText)) {
            if (user.getRoom() == null) {
                user.setRoom(Integer.parseInt(messageText));
                userService.update(user);
                bot.sendMessage(charId, Phrases.ROOM_NEW);
            } else {
                user.setRoom(Integer.parseInt(messageText));
                userService.update(user);
                bot.sendMessage(charId, Phrases.ROOM_INFO + user.getRoom() + " кабінет");
            }
            log.info(user.getFullName() + " set a new room");
        }

    }

    //user enter or change his phone number
    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId, TelegramBot bot){
        if (MessageChecker.isPhoneNumber(messageText)) {
            if (user.getPhoneNumber() == null) {
                user.setPhoneNumber(messageText);
                userService.update(user);
                bot.sendMessage(charId, Phrases.PHONE_NUMBER_NEW);
            } else {
                user.setPhoneNumber(messageText);
                userService.update(user);
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

    //show all commands
    @SneakyThrows
    public void getHelp(String messageText, long charId, TelegramBot bot){
        if (messageText.equals(Commands.HELP)) {
            bot.sendMessage(charId, Phrases.HELP);
            log.info("User (" + charId + ") press the help");
        }
    }

    //show unexpected message
    @SneakyThrows
    public void getUnexpectedMessage(String messageText, long charId, TelegramBot bot){
        if (MessageChecker.isUnexpectedMessage(messageText)) {
            bot.sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            log.info("User (" + charId + ") entered incorrect message");
        }
    }

    //press the button (lists of users)
    @SneakyThrows
    public void getListOfEmployees(String messageText, long charId, TelegramBot bot){
        if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
            bot.executeSetting(charId, ListButton.getButtonsList());
            log.info("User (" + charId + ") pressed  the button (list of employees)");
        }
    }

    //press the button (the settings)
    @SneakyThrows
    public void getSettings(String messageText, long charId, TelegramBot bot){
        if (messageText.equals(Commands.SETTINGS)){
            bot.executeSetting(charId, SettingsButton.inlineButtonsForSettings());
            log.info("User (" + charId + ") pressed the button (the settings)");
        }
    }
}

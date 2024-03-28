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
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;

@Builder
@RequiredArgsConstructor
public class BotHandlerMessage {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    public void getStart(Update update, String messageText){
        if (messageText.equals(Commands.START)) {
            new Registration(userService, notificationService, config).registerUser(update.getMessage());
        }
    }

    @SneakyThrows
    public void getFullName(User user, String messageText, long charId, String [] stringBuilder){
        if (MessageChecker.isFullName(stringBuilder, messageText)) {
            if (user.getFullName().equals("Хтось")) {
                user.setFullName(messageText);
                userService.update(user);

                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.FULL_NAME);
            } else {
                user.setFullName(messageText);
                userService.update(user);

                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
            }
        }
    }

    @SneakyThrows
    public void getAtWork(User user, long charId, String messageText){

        if (messageText.equals(Commands.AT_WORK)) {
            String s = new AtWorkService(userService).addUserAtWorkClick(user, LocalTime.now());

            if(user.getStatus().equals(Status.WORK)){
                new TelegramBot(config, userService, notificationService).sendMessage(charId, s);
            } else {
                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            }

        }
    }

    @SneakyThrows
    public void getPassword(User user, String messageText, long charId, String [] stringBuilder){
        if (MessageChecker.isPassword(stringBuilder, messageText)) {
            String password = Converter.convertPassword(messageText);

            user.setPassword(password);
            userService.save(user);

            new TelegramBot(config, userService, notificationService).sendMessage(charId, password);
        }
    }

    @SneakyThrows
    public void getRoom(User user, String messageText, long charId){

        if (MessageChecker.isARoom(messageText)) {
            if (user.getRoom() == null) {
                user.setRoom(Integer.parseInt(messageText));
                userService.update(user);
                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.ROOM_NEW);
            } else {
                user.setRoom(Integer.parseInt(messageText));
                userService.update(user);
                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.ROOM_INFO + user.getRoom() + " кабінет");
            }
        }

    }

    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId){
        if (MessageChecker.isPhoneNumber(messageText)) {
            if (user.getPhoneNumber() == null) {
                user.setPhoneNumber(messageText);
                userService.update(user);
                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.PHONE_NUMBER_NEW);
            } else {
                user.setPhoneNumber(messageText);
                userService.update(user);
                new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.PHONE_NUMBER_INFO + user.getPhoneNumber());
            }
        }
    }

    @SneakyThrows
    public void getSend(String messageText, User user){
        if (messageText.contains(Commands.SEND)){
            String message = messageText.substring(6);

            for(User user1: userService.listAll()){
                new TelegramBot(config, userService, notificationService).sendMessage(user1.getChatId(), message + "\nВід: " + user.getFullName());
            }
        }

    }

    @SneakyThrows
    public void getHelp(String messageText, long charId){
        if (messageText.equals(Commands.HELP)) {
            new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.HELP);
        }
    }

    @SneakyThrows
    public void getUnexpectedMessage(String messageText, long charId){
        if (MessageChecker.isUnexpectedMessage(messageText)) {
            new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
        }
    }

    @SneakyThrows
    public void getListOfEmployees(String messageText, long charId){
        if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
            new TelegramBot(config, userService, notificationService).executeSetting(charId, ListButton.getButtonsIfTurnOnAtNine());
        }
    }

    @SneakyThrows
    public void getSettings(String messageText, long charId){
        if (messageText.equals(Commands.SETTINGS)){
            new TelegramBot(config, userService,notificationService).executeSetting(charId, SettingsButton.inlineButtonsForSettings());
        }
    }
}

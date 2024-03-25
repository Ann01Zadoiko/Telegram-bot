package com.example.please.handler;

import com.example.please.atWork.AtWorkService;
import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalTime;

@RequiredArgsConstructor
public class BotHandlerMessage {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    @SneakyThrows
    public void getFullName(User user, String messageText, long charId){

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

    @SneakyThrows
    public void getAtWork(User user, long charId){
        String s = new AtWorkService(userService).atWorkClick(user, LocalTime.now());

        if(user.getStatus().equals(Status.WORK)){
            new TelegramBot(config, userService, notificationService).sendMessage(charId, s);
        } else {
            new TelegramBot(config, userService, notificationService).sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
        }
    }

    @SneakyThrows
    public void getPassword(User user, String messageText, long charId){
        String password = Converter.convertPassword(messageText);

        user.setPassword(password);
        userService.save(user);

        new TelegramBot(config, userService, notificationService).sendMessage(charId, password);
    }

    @SneakyThrows
    public void getRoom(User user, String messageText, long charId){
        if (user.getRoom() == null) {
            user.setRoom(Integer.parseInt(messageText));
            userService.update(user);
            new TelegramBot(config, userService, notificationService).sendMessage(charId, "Буду приходити на каву");
        } else {
            user.setRoom(Integer.parseInt(messageText));
            userService.update(user);
            new TelegramBot(config, userService, notificationService).sendMessage(charId, "Ви змінили своє місце проживання на " + user.getRoom() + " кабінет");
        }
    }

    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId){
        if (user.getPhoneNumber() == null) {
            user.setPhoneNumber(messageText);
            userService.update(user);
            new TelegramBot(config, userService, notificationService).sendMessage(charId, "Буду тепер тобі постійно звонити, мій друже");
        } else {
            user.setPhoneNumber(messageText);
            userService.update(user);
            new TelegramBot(config, userService, notificationService).sendMessage(charId, "Ви змініли свій номер на " + user.getPhoneNumber());
        }
    }

    @SneakyThrows
    public void getSend(String messageText, User user){
        String message = messageText.substring(6);

        for(User user1: userService.listAll()){
            new TelegramBot(config, userService, notificationService).sendMessage(user1.getChatId(), message + "\nВід: " + user.getFullName());
        }
    }



}

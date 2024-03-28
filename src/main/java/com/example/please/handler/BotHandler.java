package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;



@RequiredArgsConstructor
public class BotHandler {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;

    //answer from a callback
    public void getAllCallback(Update update){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        User user = userService.getByChatId(chatId);
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        Notification notification = notificationService.getNotificationByUser(user);

        BotHandlerCallback callback = BotHandlerCallback
                .builder()
                .userService(userService)
                .notificationService(notificationService)
                .config(config)
                .build();

        TelegramBot telegramBot = new TelegramBot(config, userService, notificationService);

        callback.getNine(notification, chatId, messageId, data, telegramBot);
        callback.getEight(data, notification, chatId, messageId, telegramBot);
        callback.getOff(data, notification, chatId, messageId, telegramBot);
        callback.getWork(data, user, chatId, messageId, telegramBot);
        callback.getSick(data, user, chatId, messageId, telegramBot);
        callback.getVacation(data, user, chatId, messageId, telegramBot);
        callback.getNotification(data, notification, chatId, messageId, telegramBot);
        callback.getFullName(data, user, chatId, messageId, telegramBot);
        callback.getPassword(data, user, chatId, messageId, telegramBot);
        callback.getRoom(data, user, chatId, messageId, telegramBot);
        callback.getStatus(data, user, chatId, messageId, telegramBot);
        callback.getPhoneNumber(data,user,chatId,messageId, telegramBot);
        callback.getListOfWork(data, chatId, messageId, telegramBot);
        callback.getListOfSick(data, chatId, messageId, telegramBot);
        callback.getListOfVacation(data, chatId, messageId, telegramBot);
        callback.getBackToSettings(data, chatId, messageId, telegramBot);
        callback.getBackToList(data, chatId, messageId, telegramBot);
    }

    //answer from user's message
    public void getAllMessage(Update update){
        String messageText = update.getMessage().getText();
        long charId = update.getMessage().getChatId();
        User user = userService.getByChatId(charId);
        String[] stringBuilder = messageText.split(" ");

        BotHandlerMessage message = BotHandlerMessage
                .builder()
                .userService(userService)
                .notificationService(notificationService)
                .config(config)
                .build();

        TelegramBot telegramBot = new TelegramBot(config, userService, notificationService);

        message.getStart(update, messageText, telegramBot);
        message.getFullName(user,messageText, charId, stringBuilder, telegramBot);
        message.getHelp(messageText, charId, telegramBot);
        message.getAtWork(user, charId, messageText, telegramBot);
        message.getPassword(user, messageText, charId, stringBuilder, telegramBot);
        message.getUnexpectedMessage(messageText, charId, telegramBot);
        message.getListOfEmployees(messageText, charId, telegramBot);
        message.getRoom(user, messageText, charId, telegramBot);
        message.getPhoneNumber(user, messageText, charId, telegramBot);
        message.getSend(messageText, user, telegramBot);
        message.getSettings(messageText, charId, telegramBot);
    }

}

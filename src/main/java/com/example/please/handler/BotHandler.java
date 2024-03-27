package com.example.please.handler;

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

        callback.getNine(notification, chatId, messageId, data);
        callback.getEight(data, notification, chatId, messageId);
        callback.getOff(data, notification, chatId, messageId);
        callback.getWork(data, user, chatId, messageId);
        callback.getSick(data, user, chatId, messageId);
        callback.getVacation(data, user, chatId, messageId);
        callback.getNotification(data, notification, chatId, messageId);
        callback.getFullName(data, user, chatId, messageId);
        callback.getPassword(data, user, chatId, messageId);
        callback.getRoom(data, user, chatId, messageId);
        callback.getStatus(data, user, chatId, messageId);
        callback.getPhoneNumber(data,user,chatId,messageId);
        callback.getListOfWork(data, chatId, messageId);
        callback.getListOfSick(data, chatId, messageId);
        callback.getListOfVacation(data, chatId, messageId);
        callback.getBackToSettings(data, chatId, messageId);
        callback.getBackToList(data, chatId, messageId);
    }

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

        message.getStart(update, messageText);
        message.getFullName(user,messageText, charId, stringBuilder);
        message.getHelp(messageText, charId);
        message.getAtWork(user, charId, messageText);
        message.getPassword(user, messageText, charId, stringBuilder);
        message.getUnexpectedMessage(messageText, charId);
        message.getListOfEmployees(messageText, charId);
        message.getRoom(user, messageText, charId);
        message.getPhoneNumber(user, messageText, charId);
        message.getSend(messageText, user);
        message.getSettings(messageText, charId);
    }

}

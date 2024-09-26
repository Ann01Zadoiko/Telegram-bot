package com.example.please.handler;

import com.example.please.bot.TelegramBot;
import com.example.please.config.BotConfig;
import com.example.please.handler.message.*;
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

    //answer to a callback
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

        callback.getNotification(data, notification, chatId, messageId, telegramBot);
        callback.getFullName(data, user, chatId, messageId, telegramBot);
        callback.getStatus(data, user, chatId, messageId, telegramBot);
        callback.getPhoneNumber(data,user,chatId,messageId, telegramBot);
        callback.getBackToSettings(data, chatId, messageId, telegramBot);
        callback.commandsForCallbackOfNotifications(data, notification, chatId, messageId, telegramBot);
        callback.commandsForStatuses(data, user, chatId, messageId, telegramBot);
    }

    //answer to user's message
    public void getAllMessage(Update update){
        String messageText = update.getMessage().getText();
        long charId = update.getMessage().getChatId();
        User user = userService.getByChatId(charId);
        String[] stringBuilder = messageText.split(" ");

        TelegramBot telegramBot = new TelegramBot(config, userService, notificationService);

        new MessageForFullName(userService).getFullName(user,messageText, charId, stringBuilder, telegramBot);
        new MessageForUnexpectedMessage().getUnexpectedMessage(messageText, stringBuilder, charId, telegramBot);
        new MessageForPhoneNumber(userService).getPhoneNumber(user, messageText, charId, telegramBot);
        new MessageForSettings().getSettings(messageText, charId, telegramBot);
        new MessageForNotification(notificationService).getNotification(messageText, user, charId, telegramBot);
        new MessageForOthers(userService, notificationService).commandForMessage(update, messageText,telegramBot, charId, user);
    }

}

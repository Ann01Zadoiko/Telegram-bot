package com.telegram_bot.handler;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.config.BotConfig;
import com.example.please.handler.callback.*;
import com.example.please.handler.message.*;
import com.telegram_bot.handler.callback.*;
import com.telegram_bot.handler.message.MessageForNotification;
import com.telegram_bot.handler.message.MessageForOthers;
import com.telegram_bot.handler.message.MessageForSettings;
import com.telegram_bot.handler.message.MessageForUnexpectedMessage;
import com.telegram_bot.handler.registration.Registration;
import com.telegram_bot.handler.registration.RegistrationType;
import com.telegram_bot.handler.registration.UserStateManager;
import com.telegram_bot.notification.Notification;
import com.telegram_bot.notification.NotificationService;
import com.telegram_bot.user.User;
import com.telegram_bot.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class BotHandler {

    private final UserService userService;
    private final NotificationService notificationService;
    private final BotConfig config;
    private final Registration registration;
    private final UserStateManager stateManager;

    //answer to a callback
    public void getAllCallback(Update update){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        User user = userService.getByChatId(chatId);
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        Notification notification = notificationService.getNotificationByUser(user);

        TelegramBot telegramBot = new TelegramBot(config, userService, notificationService, registration, stateManager);

        new CallbackFullName().getFullName(data, user, chatId, messageId, telegramBot);
        new CallbackListOfStatus().getStatus(data, user, chatId, messageId, telegramBot);
        new CallbackPhoneNumber().getPhoneNumber(data,user,chatId,messageId, telegramBot);
        new CallbackSettingsBack().getBackToSettings(data, chatId, messageId, telegramBot);
        new CallbackNotification(notificationService).commandsForCallbackOfNotifications(data, notification, chatId, messageId, telegramBot);
        new CallbackStatus(userService).commandsForStatuses(data, user, chatId, messageId, telegramBot);

    }

    //answer to user's message
    @SneakyThrows
    public void getAllMessage(Update update){
        String messageText = update.getMessage().getText();
        long charId = update.getMessage().getChatId();
        User user = userService.getByChatId(charId);
        String[] stringBuilder = messageText.split(" ");

        TelegramBot telegramBot = new TelegramBot(config, userService, notificationService, registration, stateManager);

        // Если пользователь в процессе регистрации, обрабатываем только нужный тип регистрации
        if (stateManager.isUserRegistering(update.getMessage().getChatId())) {
            if (update.getMessage().getText().equalsIgnoreCase("/cancel")) {
                stateManager.removeUser(update.getMessage().getChatId());
                telegramBot.sendMessage(update.getMessage().getChatId(), "❌ Регистрация отменена.");
            } else {
                log.info("🟡 Пользователь {} находится в процессе регистрации.", update.getMessage().getChatId());

                RegistrationType type = stateManager.getUserRegistrationType(update.getMessage().getChatId());

                if (type == RegistrationType.START) {
                    registration.processRegistrationStep(update.getMessage().getChatId(), update.getMessage().getText(), telegramBot);
                }


            }
            return; // Прерываем дальнейшую обработку
        }

     //   new MessageForFullName(userService).getFullName(user,messageText, charId, stringBuilder, telegramBot);
        new MessageForUnexpectedMessage().getUnexpectedMessage(messageText, stringBuilder, charId, telegramBot);
   //     new MessageForPhoneNumber(userService).getPhoneNumber(user, messageText, charId, telegramBot);
        new MessageForSettings().getSettings(messageText, charId, telegramBot);
        new MessageForNotification(notificationService).getNotification(messageText, user, charId, telegramBot);
        new MessageForOthers(userService, registration).commandForMessage(update, messageText,telegramBot, charId, user);
    }

}

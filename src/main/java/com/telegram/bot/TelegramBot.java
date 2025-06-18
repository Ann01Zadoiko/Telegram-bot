package com.telegram.bot;

import com.telegram.buttons.reply.ReplyKeyboardButtons;
import com.telegram.config.BotConfig;
import com.telegram.constant.Phrases;
import com.telegram.handler.BotHandler;
import com.telegram.handler.registration.Registration;
import com.telegram.handler.registration.UserStateManager;
import com.telegram.notification.Notification;
import com.telegram.notification.NotificationService;
import com.telegram.schedule.ReminderService;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService userService;
    private final NotificationService notificationService;
    private final Registration registration;
    private final UserStateManager stateManager;

    @SneakyThrows
    public TelegramBot(BotConfig config, UserService userService, NotificationService notificationService, Registration registration,
                       UserStateManager stateManager) {
        this.config = config;
        this.userService = userService;
        this.notificationService = notificationService;
        this.registration = registration;
        this.stateManager = stateManager;

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/status", "Інформація за день"));
        this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
    }

    //main work of bot
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotHandler botHandler = new BotHandler(userService, notificationService, config, registration, stateManager);

        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        if (update.hasMessage() && message.hasText()) {
            botHandler.getAllMessage(update);

            if (text.contains(":")) {
                String[] parts = text.split(":");

                if (parts.length == 2) {
                    try {
                        User byChatId = userService.getByChatId(chatId);
                        Notification notification = notificationService.getNotificationByUser(byChatId);
                        notification.setTimeOfNotification(text);
                        notificationService.save(notification);

                        int hour = Integer.parseInt(text.split(":")[0]);
                        int minute = Integer.parseInt(text.split(":")[1]);
                        ReminderService.scheduleReminder(hour, minute, chatId, this);
                        sendMessage(chatId, Phrases.NOTIFICATION);
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "⚠ Ошибка! Время должно быть в формате HH:MM");
                    }
                } else {
                    sendMessage(chatId, "⚠ Ошибка! Используй формат: /reminder HH:MM");
                }
            }
        }

        if (update.hasCallbackQuery()) {
            botHandler.getAllCallback(update);
        }
    }

    //send buttons after user's message
    @SneakyThrows
    public void executeSetting(long charId, InlineKeyboardMarkup markup, String text) {

        SendMessage build = SendMessage
                .builder()
                .chatId(charId)
                .text(text)
                .replyMarkup(markup)
                .build();
        execute(build);

        log.info("Reply sent: {}\nBy user: {}",build.getText(), build.getChatId());
    }

    @SneakyThrows
    public void executeEditMessage(String text, Long chatId, long messageId) {

        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .text(text)
                .build();
        execute(editMessageText);

        log.info("Reply sent: {}\nBy user: {}", editMessageText.getText(), editMessageText.getChatId());
    }

    //send message where used buttons in callback
    @SneakyThrows
    public void executeEditMessageTextWithButton(String text, Long chatId, long messageId, InlineKeyboardMarkup markup) {

        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
        execute(editMessageText);

        log.info("Reply sent: " + editMessageText.getText() + "\nBy user: " + editMessageText.getChatId());
    }

    //send message for user
    public void sendMessage(long chatId, String text) throws TelegramApiException {

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(ReplyKeyboardButtons.buttons())
                .build();
        execute(sendMessage);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

}

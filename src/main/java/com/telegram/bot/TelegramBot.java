package com.telegram.bot;

import com.telegram.buttons.Buttons;
import com.telegram.config.BotConfig;
import com.telegram.handler.BotHandler;
import com.telegram.handler.registration.Registration;
import com.telegram.handler.registration.UserStateManager;
import com.telegram.notification.NotificationService;
import com.telegram.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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

        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/status", "Інформація за день"));
        this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
    }

    //main work of bot
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotHandler botHandler = new BotHandler(userService, notificationService, config, registration, stateManager);

        if (update.hasMessage() && update.getMessage().hasText()) {
            botHandler.getAllMessage(update);
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
                .replyMarkup(Buttons.buttons())
                .build();
        execute(sendMessage);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
    }

    //registration
    public void sendMessageRegistration(long chatId, String text) throws TelegramApiException {

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
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

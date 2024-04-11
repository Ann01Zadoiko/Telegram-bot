package com.example.please.bot;

import com.example.please.buttons.*;
import com.example.please.config.BotConfig;
import com.example.please.handler.*;
import com.example.please.notification.NotificationService;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService userService;
    private final NotificationService notificationService;

    //main work of bot
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotHandler botHandler = new BotHandler(userService, notificationService, config);

        if (update.hasMessage() && update.getMessage().hasText()) {
            botHandler.getAllMessage(update);
        }

        if (update.hasCallbackQuery()) {
            botHandler.getAllCallback(update);
        }
    }

    //send buttons after user's message
    @SneakyThrows
    public void executeSetting(long charId, InlineKeyboardMarkup markup, String text){

        SendMessage build = SendMessage
                .builder()
                .chatId(charId)
                .text(text)
                .replyMarkup(markup)
                .build();
        execute(build);

        log.info("Reply sent: " + build.getText() + "\nBy user: " + build.getChatId());
    }

    //send message where used buttons in callback
    @SneakyThrows
    public void executeEditMessageTextWithButton(String text, Long chatId, long messageId, InlineKeyboardMarkup markup){

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
                .replyMarkup(Buttons.getButtons())
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

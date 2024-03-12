package com.example.please.bot;


import com.example.please.atWork.AtWorkService;
import com.example.please.command.Buttons;
import com.example.please.command.NotificationButton;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.handler.MessageChecker;
import com.example.please.user.UserService;
import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService service;
    private final AtWorkService atWorkService;
    private String list;



    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long charId = update.getMessage().getChatId();
            User user = service.getByChatId(charId);
            Long id = user.getId();
            String[] stringBuilder = messageText.split(" ");

            if (messageText.equals(Commands.START)) {
                registerUser(update.getMessage());
            }

            if (messageText.equals(Commands.MY_FULL_NAME)) {
                String date = "Ваш ПІБ: " + user.getFullName();
                sendMessage(charId, date);

                log.info("\nUser: " + service.getById(id));
            }

            if (MessageChecker.isFullName(stringBuilder, messageText) && !(messageText.equals("Список за обраний день"))) {

                if (user.getFullName().equals("Хтось")) {
                    user.setFullName(messageText);
                    service.update(user);

                    sendMessage(charId, Phrases.FULL_NAME);

                } else {
                    user.setFullName(messageText);
                    service.update(user);

                    sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
                }

            }

            if (messageText.equals(Commands.HELP)) {
                sendMessage(charId, Phrases.HELP);
            }

            if (messageText.equals(Commands.AT_WORK)) {
                String s = atWorkService.atWorkClick(id, LocalTime.now());

                sendMessage(charId, s);
            }

            if (MessageChecker.isPassword(stringBuilder, messageText)) {
                String password = Converter.convertPassword(messageText);

                user.setPassword(password);
                service.save(user);

                sendMessage(charId, password);
            }

            if (MessageChecker.isUnexpectedMessage(messageText)) {
                sendMessage(charId, "Not this time!");
            }

            if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
                sendMessage(charId, atWorkService.print(list));
            }

            if (messageText.equals(Commands.MY_PASSWORD)) {
                if (user.getPassword() == null) {
                    sendMessage(charId, "У Вас немає паролю!\nНавіщо тоді натискати цю кнопку?");
                } else {
                    sendMessage(charId, user.getPassword());
                }
            }

            if (MessageChecker.isARoom(messageText)) {
                if (user.getRoom() == null) {
                    user.setRoom(Integer.parseInt(messageText));
                    service.update(user);
                    sendMessage(charId, "Буду приходити на каву");
                } else {
                    user.setRoom(Integer.parseInt(messageText));
                    service.update(user);
                    sendMessage(charId, "Ви змінили своє місце проживання на " + user.getRoom() + " кабінет");
                }
            }

            if (MessageChecker.isPhoneNumber(messageText)) {
                if (user.getPhoneNumber() == null) {
                    user.setPhoneNumber(messageText);
                    service.update(user);
                    sendMessage(charId, "Буду тепер тобі постійно звонити, мій друже");
                } else {
                    user.setPhoneNumber(messageText);
                    service.update(user);
                    sendMessage(charId, "Ви змініли свій номер на " + user.getPhoneNumber());
                }
            }

            if (messageText.equals(Commands.ROOM)) {
                if (user.getRoom() == null) {
                    sendMessage(charId, Phrases.ROOM);
                } else {
                    sendMessage(charId, "Ви працюєте в " + user.getRoom() + " кабінеті");
                }
            }

            if (messageText.equals(Commands.PHONE_NUMBER)) {
                if (user.getPhoneNumber() == null) {
                    sendMessage(charId, Phrases.PHONE_NUMBER);
                } else {
                    sendMessage(charId, "Ваш номер телефону: " + user.getPhoneNumber());
                }
            }


            if (messageText.equals("/notification")) {

                String message = "At the moment notification work at 9 o'clock";

                SendMessage build = SendMessage
                        .builder()
                        .chatId(charId)
                        .text(message)
                        .replyMarkup(NotificationButton.inlineButtonsForNotificationIfOn())
                        .build();

                execute(build);

            }

            if (messageText.contains("/send")){
                String message = messageText.substring(6);

                for(User user1: service.listAll()){
                    sendMessage(user1.getChatId(), message);
                }
            }
        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (data.equals("9:0")) {
                sendMessage(chatId, "Something");
            }
            if (data.equals("8:0")) {
                sendMessage(chatId, "Something");
            }
            if (data.equals("off")) {
                sendMessage(chatId, "Something");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void becomeNewDay() {
        List<User> users = service.listAll();
        log.info("A new day!");
        list = "";

        for (User user : users) {
            user.setAtWork((byte) 0);
            service.update(user);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void dailyRemember() {
        List<User> users = service.listAll();

        for (User user : users) {
            if (user.getAtWork() == 0) {
                sendMessage(user.getChatId(), "Запізнюватись не гарно!");
            }
        }
    }

    public void sendMessage(long chatId, String text) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((String.valueOf(chatId)));
        sendMessage.setText(text);

        sendMessage.setReplyMarkup(Buttons.getButtons());

        execute(sendMessage);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
    }

    @SneakyThrows
    private void registerUser(Message message) {
        if (!service.existsByChatId(message.getChatId())) {
            User user = new User();
            user.setChatId(message.getChatId());
            user.setFullName("Хтось");
            service.save(user);

            sendMessage(message.getChatId(), Phrases.START_NEW_USER);

        } else {
            sendMessage(message.getChatId(), Phrases.START_OLD_USER);
        }
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

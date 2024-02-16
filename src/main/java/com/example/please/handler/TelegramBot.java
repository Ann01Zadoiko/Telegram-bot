package com.example.please.handler;


import com.example.please.config.BotConfig;
import com.example.please.user.User;
import com.example.please.user.UserRepository;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final UserService service;
    //private final UserRepository repository;


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long charId = update.getMessage().getChatId();

            if (messageText.equals("/start")){
                try {


                   // startCommandReceived(charId, update.getMessage().getChat().getUserName());

                    User user = new User();
                    user.setChatId(charId);
                    user.setUsername(update.getMessage().getChat().getUserName());
                    service.save(user);

                    startCommandReceived(charId, update.getMessage().getChat().getUserName());

                    log.info("user:" + service.listAll());

                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }

            }

            if (messageText.equals("/user")){

                try {
                    sendMessage(charId, "Something");

                    log.info("\nUser: " + service.getByChatId(charId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

//            if (messageText.contains("/fullname")) {
//                String fullName = messageText.substring(10);
//
//                User user = new User();
//
//                user.setUsername(update.getMessage().getChat().getUserName());
//                user.setId(update.getMessage().getChatId());
//                user.setFullName(fullName);
//                user.setDeparture("null");
//
//                userRepository.save(user);
//
//            //    log.info("User was saved: " + user);
//            }

//            if (messageText.contains("/departure")){
//                String departure = messageText.substring(11);
//
//                Optional<User> user = userRepository.findById(charId);
//                user.get().setDeparture(departure);
//
//                userRepository.save(user.get());
//
//             //   log.info("Departure was added to user: " + user);
//            }

        }
    }

    private void startCommandReceived(long chatId, String name) throws TelegramApiException {

        String text = "Вітаю! Я бот даного КП. На початку пропоную " +
        "Вам вести повне ПІБ  \nYour name: " + name;

        sendMessage(chatId, text);

        log.info("\nReplied to username: " + name);
    }

    private void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((String.valueOf(chatId)));
        sendMessage.setText(text);

        try {
            execute(sendMessage);
            log.info("Reply sent: " + sendMessage.getText().toString());
        } catch (TelegramApiException exception){
            log.error("Error occurred: " + exception.getMessage());
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

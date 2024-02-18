package com.example.please.handler;


import com.example.please.atWork.AtWork;
import com.example.please.atWork.AtWorkService;
import com.example.please.command.BotMenu;
import com.example.please.config.BotConfig;
import com.example.please.constant.Constance;
import com.example.please.convert.Converter;
import com.example.please.user.User;
import com.example.please.user.UserRepository;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService service;
    private final AtWorkService atWorkService;

    @SneakyThrows
    public TelegramBot(BotConfig config, UserService service, AtWorkService atWorkService) {
        this.config = config;
        this.service = service;
        this.atWorkService = atWorkService;

        this.execute(new SetMyCommands(new BotMenu().listOfCommands(), new BotCommandScopeDefault(), null));
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long charId = update.getMessage().getChatId();

            if (messageText.equals("/start private-bot")){
                registerUser(update.getMessage());
            }

            if (messageText.equals("/mydata")){
                User user = service.getByChatId(charId);
                String date = "ПІБ: " + user.getFullName() + "\nВідділ: " + user.getDeparture();
                sendMessage(charId, date);
                log.info("\nUser: " + service.getByChatId(charId));
            }

            if (messageText.contains("ПІБ:")){
                String fullName = messageText.substring(5);
                User user = service.getByChatId(charId);

                if (user.getFullName() == null){
                    user.setFullName(fullName);
                    service.save(user);
                    sendMessage(charId, fullName + Constance.FULL_NAME);
                } else {
                    user.setFullName(fullName);
                    service.save(user);
                    sendMessage(charId, Constance.FULL_NAME_NEW + fullName);
                }

                log.info("User changed his full name to " + user.getFullName() + "\nFull info about user: " + user);
            }

            if (messageText.contains("Відділ:") || messageText.contains("відділ:")){
                String departure = messageText.substring(8);
                User user = service.getByChatId(charId);

                if (user.getDeparture() == null) {
                    user.setDeparture(departure);
                    service.save(user);
                    sendMessage(charId, Constance.DEPARTURE);
                } else {
                    user.setDeparture(departure);
                    service.save(user);
                    sendMessage(charId, Constance.DEPARTURE_NEW + user.getDeparture());
                }

                log.info("User changed his departure to " + user.getDeparture() + "\nFull info about user: " + user);
            }

            if (messageText.equals("/help")){
                sendMessage(charId, Constance.HELP);
            }

            if (messageText.equals("На місці!")){
                atWorkService.atWorkClick(charId);
                sendMessage(charId, "Бажаю гарного робочого дня!");
            }

            if (messageText.contains("Пароль")){
                String convert = messageText.substring(7);

                String password = Converter.convertPassword(convert);
                sendMessage(charId, password);
            }

            if (messageText.equals("Список працівників")){

                String printList = atWorkService.printList(LocalDate.now());
                sendMessage(charId, printList);
            }

        }

    }

//    private void startCommandReceived(long chatId) throws TelegramApiException {
//
//        String text = "Вітаю! Я бот даного КП. На початку пропоную " +
//        "Вам вести повне ПІБ  \nYour name: " + name;
//
//        sendMessage(chatId, text);
//
//        log.info("\nReplied to username: " + name);
//    }

    private void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((String.valueOf(chatId)));
        sendMessage.setText(text);

        sendMessage.setReplyMarkup(Buttons.getButtons());
        execute(sendMessage);
        log.info("Reply sent: " + sendMessage.getText());
    }

    @SneakyThrows
    private void registerUser(Message message){
        if (!service.existsByChatId(message.getChatId())){
            User user = new User();
            user.setChatId(message.getChatId());
            user.setUsername(message.getChat().getUserName());
            service.save(user);

            sendMessage(message.getChatId(), Constance.START_NEW_USER);
            log.info("user:" + service.listAll());

        } else {
            User user = service.getByChatId(message.getChatId());
            String fullName = user.getFullName();
            sendMessage(message.getChatId(), fullName + Constance.START_OLD_USER);
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

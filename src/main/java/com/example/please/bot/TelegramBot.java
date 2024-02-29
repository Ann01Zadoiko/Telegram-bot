package com.example.please.bot;


import com.example.please.atWork.AtWorkService;
import com.example.please.command.BotMenu;
import com.example.please.command.Buttons;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


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


            String[] stringBuilder = messageText.split(" ");

            if (messageText.equals(Commands.START)){
                registerUser(update.getMessage());
            }

            if (messageText.equals(Commands.MY_FULL_NAME)){
                User user = service.getByChatId(charId);
                String date = "Ваш ПІБ: " + user.getFullName();
                sendMessage(charId, date);

                log.info("\nUser: " + service.getByChatId(charId));
            }


            if (stringBuilder.length > 1 && !(isACommand(messageText))) {

                User user = service.getByChatId(charId);

                if (user.getFullName() == null){
                    user.setFullName(messageText);
                    service.update(user);

                    sendMessage(charId, Phrases.FULL_NAME);

                } else {
                    user.setFullName(messageText);
                    service.update(user);

                    sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName());
                }

            }

            if (messageText.equals(Commands.HELP)){
                sendMessage(charId, Phrases.HELP);
            }

            if (messageText.equals(Commands.AT_WORK)){
                atWorkService.atWorkClick(charId);

                sendMessage(charId, "Бажаю гарного робочого дня!");
            }

            if (messageText.length() < 15 && !(isACommand(messageText)) && stringBuilder.length < 2){

                if (isCyrillic(messageText)){
                    String password = Converter.convertPassword(messageText);

                    User user = service.getByChatId(charId);
                    user.setPassword(password);
                    service.save(user);

                    sendMessage(charId, password);

                } else {
                    sendMessage(charId, "WHAT ARE YOU DOING HERE?");
                }
            }

            if (messageText.equals(Commands.LIST_OF_EMPLOYEES)){

                sendMessage(charId, atWorkService.print(list));
            }

            if (messageText.equals(Commands.MY_PASSWORD)){
                User user = service.getByChatId(charId);

                if (user.getPassword() == null){
                    sendMessage(charId, "Your password is null");
                } else {
                    sendMessage(charId, user.getPassword());
                }
            }

        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void becomeNewDay(){
        List<User> users = service.listAll();

        log.info("A new day!");

        list = "";

        log.info("List:" + list);

        for (User user: users){
            user.setAtWork(false);
            service.update(user);

            log.info(user.getFullName() + ": " + user.isAtWork());
        }

    }

    private String list;

    public boolean isCyrillic(final String iStringToCheck) {
        return iStringToCheck.matches("^[а-яґєіїА-ЯҐЄІЇ0-9.]+$");
    }

    private boolean isACommand(String message){

        if ((message.equals(Commands.MY_PASSWORD) || message.equals(Commands.AT_WORK) ||
                message.equals(Commands.START) || message.equals(Commands.HELP) ||
                message.equals(Commands.LIST_OF_EMPLOYEES) || message.equals(Commands.MY_FULL_NAME))){
            return true;
        }

        return false;
    }

    private void sendMessage(long chatId, String text) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((String.valueOf(chatId)));
        sendMessage.setText(text);

        sendMessage.setReplyMarkup(Buttons.getButtons());
        execute(sendMessage);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
    }

    @SneakyThrows
    private void registerUser(Message message){

        if (!service.existsByChatId(message.getChatId())){
            User user = new User();
            user.setChatId(message.getChatId());
            service.save(user);

            sendMessage(message.getChatId(), Phrases.START_NEW_USER);

            log.info("Users:" + service.listAll());

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

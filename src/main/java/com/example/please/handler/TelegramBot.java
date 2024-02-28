package com.example.please.handler;


import com.example.please.atWork.AtWorkService;
import com.example.please.command.BotMenu;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


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

            if (messageText.equals(Commands.START)){
                registerUser(update.getMessage());
            }

            if (messageText.equals(Commands.MY_DATA)){
                Optional<User> user = service.getById(charId);
                String date = "ПІБ: " + user.get().getFullName();
                sendMessage(charId, date);
                log.info("\nUser: " + service.getById(charId));
            }

            if (messageText.length() > 20){
                Optional<User> user = service.getById(charId);

                user.get().setFullName(messageText);
                service.save(user.get());
                sendMessage(charId, Phrases.FULL_NAME);

                log.info("User changed his full name to " + user.get().getFullName() + "\nFull info about user: " + user);
            }


            if (messageText.equals(Commands.HELP)){
                sendMessage(charId, Phrases.HELP);
            }

            if (messageText.equals(Commands.AT_WORK)){
                atWorkService.atWorkClick(charId);
                sendMessage(charId, "Бажаю гарного робочого дня!");
            }

            if (messageText.length() <= 20){
                if ( !(messageText.equals(Commands.MY_PASSWORD) || messageText.equals(Commands.AT_WORK) ||
                messageText.equals(Commands.START) || messageText.equals(Commands.HELP) ||
                messageText.equals(Commands.LIST_OF_EMPLOYEES))){
                    String password = Converter.convertPassword(messageText);

                    Optional<User> user = service.getById(charId);
                    user.get().setPassword(password);
                    service.save(user.get());

                    sendMessage(charId, password);
                }
            }

            if (messageText.equals(Commands.LIST_OF_EMPLOYEES)){

                String list = atWorkService.print();
                sendMessage(charId, list);

            }

            if (messageText.equals(Commands.MY_PASSWORD)){
                Optional<User> user = service.getById(charId);

                if (user.get().getPassword() == null){
                    sendMessage(charId, "Your password is null");
                } else {
                    sendMessage(charId, user.get().getPassword());
                }

            }

        }

    }

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
        if (!service.existsById(message.getChatId())){
            User user = new User();
            user.setId(message.getChatId());
            service.save(user);

            sendMessage(message.getChatId(), Phrases.START_NEW_USER);
            log.info("user:" + service.listAll());

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

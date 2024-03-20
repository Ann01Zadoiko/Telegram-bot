package com.example.please.bot;


import com.example.please.atWork.AtWorkService;
import com.example.please.atWork.ListOfEmployees;
import com.example.please.command.*;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.NotificationTime;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.handler.MessageChecker;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.UserService;
import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService service;
    private final AtWorkService atWorkService;
    private final NotificationService notificationService;

    @SneakyThrows
    public TelegramBot(BotConfig config, UserService service, AtWorkService atWorkService, NotificationService notificationService) {
        this.config = config;
        this.service = service;
        this.atWorkService = atWorkService;
        this.notificationService = notificationService;

        this.execute(new SetMyCommands(new BotMenu().listOfCommands(), new BotCommandScopeDefault(), null));
    }

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
                sendMessage(charId, "WHAT ARE YOU DOING HERE!");
            }

            if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
                sendMessage(charId, atWorkService.print());
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

            if (messageText.contains("/send")){
                String message = messageText.substring(6);

                for(User user1: service.listAll()){
                    sendMessage(user1.getChatId(), message + "\nВід: " + user.getFullName());
                }
            }

            if (messageText.equals(Commands.SETTINGS)){

                SendMessage build = SendMessage
                        .builder()
                        .chatId(charId)
                        .text("Choose one of the list")
                        .replyMarkup(SettingsButton.inlineButtonsForSettings())
                        .build();

                execute(build);
            }

            if (messageText.equals(Commands.LIST_OF_VACATION)){
                sendMessage(charId, new ListOfEmployees(service).printEmployeesVacation());
            }

            if (messageText.equals(Commands.LIST_OF_SICK)){
                sendMessage(charId, new ListOfEmployees(service).printEmployeesSick());
            }

        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = service.getByChatId(chatId);
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            Notification notification = notificationService.getNotificationByUser(user);

            if (data.equals("9:00")) {

                notification.setTimeOfNotification("0 0 9 * * MON-FRI");
                notification.setTurnOn(true);
                notificationService.save(notification);

                executeEditMessageText("You change your notification at 9 am", chatId, messageId);
            }
            if (data.equals("8:00")) {

                notification.setTimeOfNotification("0 0 8 * * MON-FRI");
                notification.setTurnOn(true);
                notificationService.save(notification);

                executeEditMessageText("You change your notification at 8 am", chatId, messageId);
            }
            if (data.equals("off")) {

                notification.setTurnOn(false);
                notificationService.save(notification);

                executeEditMessageText("You turned off the notification", chatId, messageId);
            }

            if (data.equals("WORK")) {

                user.setStatus(Status.WORK);
                service.save(user);

                executeEditMessageText( "Now you are " + user.getStatus(), chatId, messageId);
            }

            if (data.equals("SICK")) {

                user.setStatus(Status.SICK);
                service.save(user);

                executeEditMessageText( "Now you are " + user.getStatus(), chatId, messageId);
            }

            if (data.equals("VACATION")) {

                user.setStatus(Status.VACATION);
                service.save(user);

                executeEditMessageText( "Now you are " + user.getStatus(), chatId, messageId);

            }

            if (data.equals("notification")){

                if (notification.getTurnOn() && notification.getTimeOfNotification().contains("9")){

                   EditMessageText editMessageText = EditMessageText
                           .builder()
                           .chatId(chatId)
                           .text("At the moment notification work at 9 o'clock")
                           .replyMarkup(NotificationButton.getButtonsIfTurnOnAtNine())
                           .messageId((int) messageId)
                           .build();

                   execute(editMessageText);
                }

                if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){

                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(chatId)
                            .text("At the moment notification work at 8 o'clock")
                            .replyMarkup(NotificationButton.getButtonsIfTurnOnAtEight())
                            .messageId((int) messageId)
                            .build();

                    execute(editMessageText);
                }

                if (!notification.getTurnOn()){

                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(chatId)
                            .text("At the moment the notification is turned off")
                            .replyMarkup(NotificationButton.getButtonsIfTurnOff())
                            .messageId((int) messageId)
                            .build();

                    execute(editMessageText);
                }
            }

            if (data.equals("full name")){
                String date = "Ваш ПІБ: " + user.getFullName();
                executeEditMessageText(date, chatId, messageId);
            }

            if (data.equals("password")){
                if (user.getPassword() == null) {
                    executeEditMessageText("У Вас немає паролю!\nНавіщо тоді натискати цю кнопку?", chatId, messageId);
                } else {
                    executeEditMessageText("Your password: " + user.getPassword(), chatId, messageId);
                }
            }

            if (data.equals("room")){
                if (user.getRoom() == null) {
                    executeEditMessageText(Phrases.ROOM, chatId, messageId);
                } else {
                    executeEditMessageText("Ви працюєте в " + user.getRoom() + " кабінеті", chatId, messageId);
                }
            }

            if (data.equals("status")){
                if (user.getStatus().equals(Status.WORK)){
                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(chatId)
                            .text("At the moment you are " + user.getStatus())
                            .replyMarkup(StatusButton.getButtonsIfWork())
                            .messageId((int) messageId)
                            .build();

                    execute(editMessageText);
                }

                if (user.getStatus().equals(Status.SICK)){
                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(chatId)
                            .text("At the moment you are " + user.getStatus())
                            .replyMarkup(StatusButton.getButtonsIfSick())
                            .messageId((int) messageId)
                            .build();

                    execute(editMessageText);
                }

                if (user.getStatus().equals(Status.VACATION)){
                    EditMessageText editMessageText = EditMessageText
                            .builder()
                            .chatId(chatId)
                            .text("At the moment you are " + user.getStatus())
                            .replyMarkup(StatusButton.getButtonsIfVacation())
                            .messageId((int) messageId)
                            .build();

                    execute(editMessageText);
                }
            }

            if (data.equals("phone number")){
                if (user.getPhoneNumber() == null) {
                    executeEditMessageText(Phrases.PHONE_NUMBER, chatId, messageId);
                } else {
                    executeEditMessageText("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId);
                }
            }
        }
    }

    @SneakyThrows
    private void executeEditMessageText(String text, Long chatId, long messageId){
        EditMessageText messageText = new EditMessageText();
        messageText.setChatId(String.valueOf(chatId));
        messageText.setText(text);
        messageText.setMessageId((int) messageId);

        execute(messageText);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void becomeNewDay() {
        List<User> users = service.listAll();
        log.info("A new day!");

        for (User user : users) {
            user.setAtWork((byte) 0);
            service.update(user);
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.NINE)
    public void dailyRememberAtNine() {
        List<User> users = service.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);
            if (user.getAtWork() == 0 && notification.getTurnOn()
                    && notification.getTimeOfNotification().contains("9")
            && user.getStatus().equals(Status.WORK)) {
                sendMessage(user.getChatId(), "Запізнюватись не гарно!");
            }
        }
    }

    @SneakyThrows
    @Scheduled(cron = NotificationTime.EIGHT)
    public void dailyRememberAtEight() {
        List<User> users = service.listAll();

        for (User user : users) {
            Notification notification = notificationService.getNotificationByUser(user);
            if (user.getAtWork() == 0 && notification.getTurnOn()
                    && notification.getTimeOfNotification().contains("8")
                    && user.getStatus().equals(Status.WORK)) {
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
            user.setStatus(Status.WORK);
            service.save(user);

            Notification notification = new Notification();
            notification.setTurnOn(true);
            notification.setTimeOfNotification("0 0 9 * * MON-FRI");
            notification.setUser(user);
            notificationService.save(notification);

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

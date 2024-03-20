package com.example.please.bot;


import com.example.please.atWork.AtWorkService;
import com.example.please.atWork.ListOfEmployees;
import com.example.please.command.*;
import com.example.please.config.BotConfig;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.convert.Converter;
import com.example.please.handler.MessageChecker;

import com.example.please.handler.Registration;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.Status;
import com.example.please.user.UserService;
import com.example.please.user.User;
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

import java.time.LocalTime;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService userService;
    private final NotificationService notificationService;

    @SneakyThrows
    public TelegramBot(BotConfig config, UserService service, NotificationService notificationService) {
        this.config = config;
        this.userService = service;

        this.notificationService = notificationService;

        this.execute(new SetMyCommands(
                new BotMenu().listOfCommands(),
                new BotCommandScopeDefault(),
                null));
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long charId = update.getMessage().getChatId();
            User user = userService.getByChatId(charId);
            String[] stringBuilder = messageText.split(" ");

            if (messageText.equals(Commands.START)) {
                new Registration(userService, notificationService, config).registerUser(update.getMessage());
            }

            if (MessageChecker.isFullName(stringBuilder, messageText)) {

                if (user.getFullName().equals("Хтось")) {
                    user.setFullName(messageText);
                    userService.update(user);

                    sendMessage(charId, Phrases.FULL_NAME);
                } else {
                    user.setFullName(messageText);
                    userService.update(user);

                    sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
                }

            }

            if (messageText.equals(Commands.HELP)) {
                sendMessage(charId, Phrases.HELP);
            }

            if (messageText.equals(Commands.AT_WORK)) {

                String s = new AtWorkService(userService).atWorkClick(user, LocalTime.now());

                if(user.getStatus().equals(Status.WORK)){
                    sendMessage(charId, s);
                } else {
                    sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
                }

            }

            if (MessageChecker.isPassword(stringBuilder, messageText)) {
                String password = Converter.convertPassword(messageText);

                user.setPassword(password);
                userService.save(user);

                sendMessage(charId, password);
            }

            if (MessageChecker.isUnexpectedMessage(messageText)) {
                sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            }

            if (messageText.equals(Commands.LIST_OF_EMPLOYEES)) {
                sendMessage(charId, new ListOfEmployees(userService).printEmployeeWork());
            }

            if (MessageChecker.isARoom(messageText)) {
                if (user.getRoom() == null) {
                    user.setRoom(Integer.parseInt(messageText));
                    userService.update(user);
                    sendMessage(charId, "Буду приходити на каву");
                } else {
                    user.setRoom(Integer.parseInt(messageText));
                    userService.update(user);
                    sendMessage(charId, "Ви змінили своє місце проживання на " + user.getRoom() + " кабінет");
                }
            }

            if (MessageChecker.isPhoneNumber(messageText)) {
                if (user.getPhoneNumber() == null) {
                    user.setPhoneNumber(messageText);
                    userService.update(user);
                    sendMessage(charId, "Буду тепер тобі постійно звонити, мій друже");
                } else {
                    user.setPhoneNumber(messageText);
                    userService.update(user);
                    sendMessage(charId, "Ви змініли свій номер на " + user.getPhoneNumber());
                }
            }

            if (messageText.contains("/send")){
                String message = messageText.substring(6);

                for(User user1: userService.listAll()){
                    sendMessage(user1.getChatId(), message + "\nВід: " + user.getFullName());
                }
            }

            if (messageText.equals(Commands.SETTINGS)){

                SendMessage build = SendMessage
                        .builder()
                        .chatId(charId)
                        .text("Оберить одну зі списку:")
                        .replyMarkup(SettingsButton.inlineButtonsForSettings())
                        .build();

                execute(build);

            }

            if (messageText.equals(Commands.LIST_OF_VACATION)){
                sendMessage(charId, new ListOfEmployees(userService).printEmployeesVacation());
            }

            if (messageText.equals(Commands.LIST_OF_SICK)){
                sendMessage(charId, new ListOfEmployees(userService).printEmployeesSick());
            }

        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userService.getByChatId(chatId);
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            Notification notification = notificationService.getNotificationByUser(user);

            if (data.equals("9:00")) {

                notification.setTimeOfNotification("0 0 9 * * MON-FRI");
                notification.setTurnOn(true);
                notificationService.save(notification);

                executeEditMessageText("Ви змінили своє нагадування о 9 ранку", chatId, messageId);
            }
            if (data.equals("8:00")) {

                notification.setTimeOfNotification("0 0 8 * * MON-FRI");
                notification.setTurnOn(true);
                notificationService.save(notification);

                executeEditMessageText("Ви змінили своє нагадування о 8 ранку", chatId, messageId);
            }
            if (data.equals("off")) {

                notification.setTurnOn(false);
                notificationService.save(notification);

                executeEditMessageText("Ви вимкнути нагадування", chatId, messageId);
            }

            if (data.equals("WORK")) {

                user.setStatus(Status.WORK);
                userService.save(user);

                executeEditMessageText( Phrases.STATUS + "працюєте", chatId, messageId);
            }

            if (data.equals("SICK")) {

                user.setStatus(Status.SICK);
                userService.save(user);

                executeEditMessageText( Phrases.STATUS + "на лікарняному" , chatId, messageId);
            }

            if (data.equals("VACATION")) {

                user.setStatus(Status.VACATION);
                userService.save(user);

                executeEditMessageText( Phrases.STATUS + "у відпустці", chatId, messageId);

            }

            if (data.equals("notification")){

                if (notification.getTurnOn() && notification.getTimeOfNotification().contains("9")){
                    executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 9 ранку", chatId, messageId, NotificationButton.getButtonsIfTurnOnAtNine());
                }

                if (notification.getTurnOn() && notification.getTimeOfNotification().contains("8")){
                    executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, NotificationButton.getButtonsIfTurnOnAtEight());
                }

                if (!notification.getTurnOn()){
                    executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, NotificationButton.getButtonsIfTurnOff());
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
                    executeEditMessageText( user.getPassword(), chatId, messageId);
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
                    executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, StatusButton.getButtonsIfWork());

                }

                if (user.getStatus().equals(Status.SICK)){
                    executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, StatusButton.getButtonsIfSick());
                }

                if (user.getStatus().equals(Status.VACATION)){
                    executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, StatusButton.getButtonsIfVacation());
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
        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .text(text)
                .build();

        execute(editMessageText);
    }

    @SneakyThrows
    private void executeEditMessageTextWithButton(String text, Long chatId, long messageId, InlineKeyboardMarkup markup){
        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();

        execute(editMessageText);
    }

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

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

}

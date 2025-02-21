package com.example.please.handler.message;

import com.example.please.atWork.AtWorkService;
import com.example.please.bot.TelegramBot;
import com.example.please.constant.Commands;
import com.example.please.handler.Registration;
import com.example.please.notification.NotificationService;
import com.example.please.printer.PrinterByStatus;
import com.example.please.printer.PrinterListOfEmployees;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MessageForOthers {

    private final UserService userService;
    private final NotificationService notificationService;

    @SneakyThrows
    public void commandForMessage(Update update, String messageText, TelegramBot bot, long chatId, User user){

        PrinterByStatus printer = new PrinterByStatus();
        List<User> users = userService.listAll();

        switch (messageText){
            case Commands.START_PRIVATE -> new Registration(userService, notificationService).start(update.getMessage(), bot);
            case Commands.STATUS -> bot.sendMessage(chatId, printer.printStatus(LocalDate.now(), users));
            case Commands.AT_WORK -> {
                String s = new AtWorkService(userService).addUserToTheList(user, LocalTime.now());
                if(user.getStatusEnum().equals(StatusEnum.WORK) || user.getStatusEnum().equals(StatusEnum.REMOTE)){
                    log.info(user.getFullName() + " is at work");
                    bot.sendMessage(chatId, s);
                } else {
                    bot.sendMessage(chatId, "Ти не маєшь права натискати на цю кнопку!");
                }
            }
            case Commands.LIST_OF_EMPLOYEES -> bot.sendMessage(chatId, new PrinterListOfEmployees(userService).showAllUsersOfTheDay());
        }
    }
}

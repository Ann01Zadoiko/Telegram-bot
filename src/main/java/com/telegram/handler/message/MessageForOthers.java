package com.telegram.handler.message;

import com.telegram.atwork.AtWorkService;
import com.telegram.bot.TelegramBot;
import com.telegram.constant.Commands;
import com.telegram.handler.registration.Registration;
import com.telegram.printer.PrinterByStatus;
import com.telegram.printer.PrinterListOfEmployees;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;
import com.telegram.user.UserService;
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
    private final Registration registration;

    @SneakyThrows
    public void commandForMessage(Update update, String messageText, TelegramBot bot, long chatId, User user){

        PrinterByStatus printer = new PrinterByStatus();
        List<User> users = userService.listAll();

        switch (messageText){
            case Commands.START_PRIVATE -> registration.startRegistration(chatId, bot);
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

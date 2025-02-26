package com.telegram_bot.handler.message;

import com.telegram_bot.atWork.AtWorkService;
import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.constant.Commands;
import com.telegram_bot.handler.registration.Registration;
import com.telegram_bot.printer.PrinterByStatus;
import com.telegram_bot.printer.PrinterListOfEmployees;
import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;
import com.telegram_bot.user.UserService;
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

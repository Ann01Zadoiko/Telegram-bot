package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Commands;
import com.telegram.printer.PrinterListOfEmployees;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class MessageForList {

    private final UserService userService;

    @SneakyThrows
    public void commandForMessage(String messageText, TelegramBot bot, long chatId){
        if (messageText.equals(Commands.LIST_OF_EMPLOYEES)){
            bot.sendMessage(chatId, new PrinterListOfEmployees(userService).showAllUsersOfTheDay());
            log.info("User ({}) enter list of employees", chatId);
        }
    }
}

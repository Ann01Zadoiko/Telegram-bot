package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Commands;
import com.telegram.printer.PrinterByStatus;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MessageForStatus {

    private final UserService userService;

    @SneakyThrows
    public void commandForMessage(String messageText, TelegramBot bot, long chatId){

        PrinterByStatus printer = new PrinterByStatus();
        List<User> users = userService.listAll();

        if (messageText.equals(Commands.STATUS)){
            bot.sendMessage(chatId, printer.printStatus(LocalDate.now(), users));
            log.info("User ({}) enter status", chatId);
        }
    }
}

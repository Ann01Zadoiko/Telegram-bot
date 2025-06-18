package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Commands;
import com.telegram.constant.Phrases;
import com.telegram.handler.registration.Registration;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageForStart {

    private final UserService userService;
    private final Registration registration;

    @SneakyThrows
    public void commandForMessage(String messageText, TelegramBot bot, long chatId){

        if (messageText.equals(Commands.START_PRIVATE)){
            if (userService.existsByChatId(chatId)){
                bot.sendMessage(chatId, Phrases.START_OLD_USER);
            }
            registration.startRegistration(chatId, bot);
            log.info("User ({}) enter 'start'", chatId);
        }
    }
}

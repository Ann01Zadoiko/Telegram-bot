package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.constant.Phrases;
import com.example.please.constant.Steps;
import com.example.please.handler.MessageChecker;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageForFullName {

    private final UserService userService;

    @SneakyThrows
    public void getFullName(User user, String messageText, long charId, String [] stringBuilder, TelegramBot bot){

        if (MessageChecker.isFullName(stringBuilder, messageText)) {

            if (user.getFullName() == null){
                user.setFullName(messageText);
                userService.save(user);
                bot.sendMessageRegistration(charId, Steps.STEP_1);
            } else {
                user.setFullName(messageText);
                userService.save(user);
                bot.sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
            }

            log.info("User set new full name: " + user.getFullName());
        }
    }

}

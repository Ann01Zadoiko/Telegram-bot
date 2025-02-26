package com.telegram_bot.handler.message;

import com.telegram_bot.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageForFullName {

    private final UserService userService;

//    @SneakyThrows
//    public void getFullName(User user, String messageText, long charId, String [] stringBuilder, TelegramBot bot){
//
//        if (MessageChecker.isFullName(stringBuilder, messageText)) {
//
//            if (user.getFullName() == null){
//                user.setFullName(messageText);
//                userService.save(user);
//                bot.sendMessageRegistration(charId, Steps.STEP_1);
//            } else {
//                user.setFullName(messageText);
//                userService.save(user);
//                bot.sendMessage(charId, Phrases.FULL_NAME_NEW + user.getFullName().toUpperCase());
//            }
//
//            log.info("User set new full name: {}", user.getFullName());
//        }
//    }

}

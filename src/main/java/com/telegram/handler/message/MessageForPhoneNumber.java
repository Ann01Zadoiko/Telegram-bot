package com.telegram.handler.message;

import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class MessageForPhoneNumber {

    private final UserService userService;

//    @SneakyThrows
//    public void getPhoneNumber(User user, String messageText, long charId, TelegramBot bot){
//
//        if (MessageChecker.isPhoneNumber(messageText)) {
//
//            if (user.getPhoneNumber() == null){
//                user.setPhoneNumber(messageText);
//                userService.save(user);
//
//                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
//                enumSet.remove(UserStatus.BACK);
//
//                bot.executeSetting(charId, new SettingsButton().getButtonsDifferentCount(enumSet), Steps.STEP_2);
//            } else {
//                user.setPhoneNumber(messageText);
//                userService.save(user);
//                bot.sendMessage(charId, Phrases.PHONE_NUMBER_INFO + user.getPhoneNumber());
//            }
//
//            log.info("{} set a new phone number", user.getFullName());
//        }
//    }
}

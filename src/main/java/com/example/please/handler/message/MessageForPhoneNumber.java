package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Phrases;
import com.example.please.constant.Steps;
import com.example.please.constant.UserStatus;
import com.example.please.handler.MessageChecker;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.util.EnumSet;



@Slf4j
@RequiredArgsConstructor
public class MessageForPhoneNumber {

    private final UserService userService;

    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId, TelegramBot bot){

        if (MessageChecker.isPhoneNumber(messageText)) {

            if (user.getPhoneNumber() == null){
                user.setPhoneNumber(messageText);
                userService.save(user);

                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.BACK);

                bot.executeSetting(charId, new SettingsButton().getButtonsDifferentCount(enumSet), Steps.STEP_2);
            } else {
                user.setPhoneNumber(messageText);
                userService.save(user);
                bot.sendMessage(charId, Phrases.PHONE_NUMBER_INFO + user.getPhoneNumber());
            }

            log.info("{} set a new phone number", user.getFullName());
        }
    }
}

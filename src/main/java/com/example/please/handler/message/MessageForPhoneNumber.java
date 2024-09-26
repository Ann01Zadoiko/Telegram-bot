package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Callback;
import com.example.please.constant.Phrases;
import com.example.please.constant.Steps;
import com.example.please.handler.MessageChecker;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class MessageForPhoneNumber {

    private final UserService userService;

    @SneakyThrows
    public void getPhoneNumber(User user, String messageText, long charId, TelegramBot bot){
        List<String> list = new ArrayList<>();
        list.add(Callback.WORK);
        list.add(Callback.REMOTE);
        list.add(Callback.SICK);
        list.add(Callback.VACATION);
        list.add(Callback.BUSINESS_TRIP);

        if (MessageChecker.isPhoneNumber(messageText)) {

            if (user.getPhoneNumber() == null){
                user.setPhoneNumber(messageText);
                userService.save(user);

                bot.executeSetting(charId, SettingsButton.getButtonsDifferentCount(list), Steps.STEP_2);
            } else {
                user.setPhoneNumber(messageText);
                userService.save(user);
                bot.sendMessage(charId, Phrases.PHONE_NUMBER_INFO + user.getPhoneNumber());
            }

            log.info(user.getFullName() + " set a new phone number");
        }
    }
}

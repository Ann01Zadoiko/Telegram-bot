package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.Phrases;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;

@Slf4j
public class CallbackPhoneNumber {

    public void getPhoneNumber(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.PHONE_NUMBER.getName())){
            Set<UserStatus> enumSet = EnumSet.of(UserStatus.BACK);
            InlineKeyboardSettingsButton<UserStatus> button = new InlineKeyboardSettingsButton<>();

            if (user.getPhoneNumber() == null) {
                log.info("{} doesn't have a phone number", user.getFullName());
                bot.executeEditMessageTextWithButton(Phrases.PHONE_NUMBER, chatId, messageId, button.getButtonsDifferentCount(enumSet));
            } else {
                log.info("Show the phone number for {}", user.getFullName());
                bot.executeEditMessageTextWithButton("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId, button.getButtonsDifferentCount(enumSet));
            }
        }
    }
}

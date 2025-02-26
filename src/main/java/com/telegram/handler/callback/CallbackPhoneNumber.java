package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.SettingsButton;
import com.telegram.constant.Phrases;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
public class CallbackPhoneNumber {

    public void getPhoneNumber(String data, User user, long chatId, long messageId, TelegramBot bot){

        if (data.equals(Settings.PHONE_NUMBER.getName())){
            EnumSet<UserStatus> enumSet = EnumSet.of(UserStatus.BACK);

            if (user.getPhoneNumber() == null) {
                log.info("{} doesn't have a phone number", user.getFullName());
             //   bot.executeEditMessageTextWithButton(Phrases.PHONE_NUMBER, chatId, messageId, BackButton.getBackToSettings());
                bot.executeEditMessageTextWithButton(Phrases.PHONE_NUMBER, chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            } else {
                log.info("Show the phone number for {}", user.getFullName());
            //    bot.executeEditMessageTextWithButton("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId, BackButton.getBackToSettings());
                bot.executeEditMessageTextWithButton("Ваш номер телефону: " + user.getPhoneNumber(), chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }
        }
    }
}

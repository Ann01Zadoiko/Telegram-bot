package com.example.please.handler.callback;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Settings;
import com.example.please.constant.UserStatus;
import com.example.please.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
public class CallbackFullName {

    public void getFullName(String data, User user, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Settings.FULL_NAME.getName())){
            String date = "Ваш ПІБ: " + user.getFullName();
            log.info("Show to user his full name: {}", user.getFullName());

         //   bot.executeEditMessageTextWithButton(date, chatId, messageId, BackButton.getBackToSettings());
            EnumSet<UserStatus> enumSet = EnumSet.of(UserStatus.BACK);
            bot.executeEditMessageTextWithButton(date, chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
        }
    }
}

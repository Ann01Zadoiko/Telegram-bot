package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.SettingsButton;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.user.User;
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

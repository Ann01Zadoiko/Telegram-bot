package com.telegram_bot.handler.callback;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.buttons.SettingsButton;
import com.telegram_bot.constant.Settings;
import com.telegram_bot.constant.UserStatus;
import com.telegram_bot.user.User;
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

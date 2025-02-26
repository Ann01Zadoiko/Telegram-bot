package com.telegram_bot.handler.callback;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.buttons.SettingsButton;
import com.telegram_bot.constant.Phrases;
import com.telegram_bot.constant.Settings;
import com.telegram_bot.constant.UserStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
public class CallbackSettingsBack {

    public void getBackToSettings(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(UserStatus.BACK.getName())){
            log.info("User (" + chatId + ") pressed button to back at the settings");
            EnumSet<Settings> enumSet = EnumSet.allOf(Settings.class);
            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
        }
    }
}

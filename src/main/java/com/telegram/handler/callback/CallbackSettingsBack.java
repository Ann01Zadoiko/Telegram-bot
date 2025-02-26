package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.SettingsButton;
import com.telegram.constant.Phrases;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
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

package com.example.please.handler.callback;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Phrases;
import com.example.please.constant.Settings;
import com.example.please.constant.UserStatus;
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

package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.Phrases;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;

@Slf4j
public class CallbackSettingsBack {

    public void getBackToSettings(String data, long chatId, long messageId, TelegramBot bot){
        if (data.equals(UserStatus.BACK.getName())){
            log.info("User ({}) pressed button to back at the settings", chatId);
            Set<Settings> enumSet = EnumSet.allOf(Settings.class);

            InlineKeyboardSettingsButton<Settings> button = new InlineKeyboardSettingsButton<>();
            bot.executeEditMessageTextWithButton(Phrases.CHOOSE, chatId,messageId, button.getButtonsDifferentCount(enumSet));
        }
    }
}

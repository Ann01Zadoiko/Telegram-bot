package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.Commands;
import com.telegram.constant.Phrases;
import com.telegram.constant.Settings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;


@Slf4j
public class MessageForSettings {

    @SneakyThrows
    public void getSettings(String messageText, long charId, TelegramBot bot){
        if (messageText.equals(Commands.SETTINGS)){
            Set<Settings> enumSet = EnumSet.allOf(Settings.class);
            InlineKeyboardSettingsButton<Settings> button = new InlineKeyboardSettingsButton<>();
            bot.executeSetting(charId, button.getButtonsDifferentCount(enumSet), Phrases.CHOOSE);
            log.info("User ({}) pressed the button (the settings)", charId);
        }
    }
}

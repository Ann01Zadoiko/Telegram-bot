package com.telegram_bot.handler.message;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.buttons.SettingsButton;
import com.telegram_bot.constant.Commands;
import com.telegram_bot.constant.Phrases;
import com.telegram_bot.constant.Settings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;


@Slf4j
public class MessageForSettings {

    @SneakyThrows
    public void getSettings(String messageText, long charId, TelegramBot bot){
        if (messageText.equals(Commands.SETTINGS)){
            EnumSet<Settings> enumSet = EnumSet.allOf(Settings.class);
            bot.executeSetting(charId, new SettingsButton().getButtonsDifferentCount(enumSet), Phrases.CHOOSE);
            log.info("User ({}) pressed the button (the settings)", charId);
        }
    }
}

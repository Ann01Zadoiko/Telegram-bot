package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.constant.Settings;
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

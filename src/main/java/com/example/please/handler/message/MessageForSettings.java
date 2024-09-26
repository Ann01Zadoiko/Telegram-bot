package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Commands;
import com.example.please.constant.Phrases;
import com.example.please.constant.Settings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MessageForSettings {

    @SneakyThrows
    public void getSettings(String messageText, long charId, TelegramBot bot){

        List<String> list = new ArrayList<>();
        list.add(Settings.FULL_NAME);
        list.add(Settings.PHONE_NUMBER);
        list.add(Settings.STATUS);

        if (messageText.equals(Commands.SETTINGS)){
            bot.executeSetting(charId, SettingsButton.getButtonsDifferentCount(list), Phrases.CHOOSE);
            log.info("User (" + charId + ") pressed the button (the settings)");
        }
    }
}

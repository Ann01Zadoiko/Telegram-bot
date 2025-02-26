package com.telegram.buttons;

import com.telegram.constant.EnumNotification;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.handler.checker.MessageChecker;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class SettingsButton <T extends Enum<T>> {

    public SettingsButton(){}

    public InlineKeyboardMarkup getButtonsDifferentCount(EnumSet<T> enumSet){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (T enu: enumSet){
            List<InlineKeyboardButton> buttons = List.of();

            if (MessageChecker.isEnumValueStatus(enu.toString())){
                UserStatus[] values = UserStatus.values();
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == enu){
                        buttons = CreatorButton.createButton(values[i].getName());
                    }
                }
            }

            if (MessageChecker.isEnumValueNotification(enu.toString())){
                EnumNotification[] values = EnumNotification.values();
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == enu){
                        buttons = CreatorButton.createButton(values[i].getTime());
                    }
                }
            }

            if (MessageChecker.isEnumValueSetting(enu.toString())){
                Settings[] values = Settings.values();
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == enu){
                        buttons = CreatorButton.createButton(values[i].getName());
                    }
                }
            }

            if (!MessageChecker.isEnumValueStatus(enu.toString()) &&
                !MessageChecker.isEnumValueNotification(enu.toString()) &&
                !MessageChecker.isEnumValueSetting(enu.toString())){
                buttons = CreatorButton.createButton(enu.toString());
            }

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }

}

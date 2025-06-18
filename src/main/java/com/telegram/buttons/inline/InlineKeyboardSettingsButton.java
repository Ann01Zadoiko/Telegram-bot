package com.telegram.buttons.inline;

import com.telegram.constant.EnumNotification;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.handler.checker.CheckerEnumNotification;
import com.telegram.handler.checker.CheckerEnumStatus;
import com.telegram.handler.checker.CheckerSettings;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class InlineKeyboardSettingsButton<T extends Enum<T>> {

    public InlineKeyboardMarkup getButtonsDifferentCount(Set<T> enumSet){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (T enu: enumSet){
            List<InlineKeyboardButton> buttons = List.of();

            if (CheckerEnumStatus.isEnumValueStatus(enu.toString())){
                UserStatus[] values = UserStatus.values();
                for (UserStatus enums : values) {
                    if (enums == enu) {
                        buttons = InlineKeyboardButtons.createButton(enums.getName());
                    }
                }
            }

            if (CheckerEnumNotification.isEnumValueNotification(enu.toString())){
                EnumNotification[] values = EnumNotification.values();
                for (EnumNotification enums : values) {
                    if (enums == enu) {
                        buttons = InlineKeyboardButtons.createButton(enums.getTime());
                    }
                }
            }

            if (CheckerSettings.isEnumValueSetting(enu.toString())){
                Settings[] values = Settings.values();
                for (Settings enums : values) {
                    if (enums == enu) {
                        buttons = InlineKeyboardButtons.createButton(enums.getName());
                    }
                }
            }

            if (!CheckerEnumStatus.isEnumValueStatus(enu.toString()) &&
                !CheckerEnumNotification.isEnumValueNotification(enu.toString()) &&
                !CheckerSettings.isEnumValueSetting(enu.toString())){
                buttons = InlineKeyboardButtons.createButton(enu.toString());
            }

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }

}

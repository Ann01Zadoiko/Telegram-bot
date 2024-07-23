package com.example.please.buttons;

import com.example.please.constant.Settings;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton {

    private SettingsButton(){}

    public static InlineKeyboardMarkup getButtonsDifferentCount(List<String> list){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            InlineKeyboardButton button = InlineKeyboardButton
                    .builder()
                    .text(list.get(i))
                    .callbackData(list.get(i))
                    .build();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(button);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }

}

package com.example.please.buttons;

import com.example.please.constant.Settings;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton {

    private SettingsButton(){}


    //list of buttons for settings
    public static InlineKeyboardMarkup inlineButtonsForSettings(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(Settings.FULL_NAME)
                .callbackData(Settings.FULL_NAME)
                .build();
        InlineKeyboardButton button5 = InlineKeyboardButton
                .builder()
                .text(Settings.PHONE_NUMBER)
                .callbackData(Settings.PHONE_NUMBER)
                .build();
        InlineKeyboardButton button6 = InlineKeyboardButton
                .builder()
                .text(Settings.STATUS)
                .callbackData(Settings.STATUS)
                .build();


        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button2);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button5);

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button6);


        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(buttons1);
        row.add(buttons2);
        row.add(buttons3);

        markup.setKeyboard(row);

        return markup;
    }

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

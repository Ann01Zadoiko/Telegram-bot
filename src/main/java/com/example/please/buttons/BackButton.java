package com.example.please.buttons;

import com.example.please.constant.Callback;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BackButton {

    private BackButton(){}

    //button for back to list of users
    public static InlineKeyboardMarkup getBackToList(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(Callback.BACK)
                .callbackData(Callback.BACK_TO_LIST)
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(buttons);

        markup.setKeyboard(row);

        return markup;
    }

    //button for back to the settings
    public static InlineKeyboardMarkup getBackToSettings(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(Callback.BACK)
                .callbackData(Callback.BACK_TO_SETTINGS)
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(buttons);

        markup.setKeyboard(row);

        return markup;
    }

}


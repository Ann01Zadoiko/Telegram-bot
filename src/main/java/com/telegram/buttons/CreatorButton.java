package com.telegram.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CreatorButton {

    private CreatorButton(){}

    public static List<InlineKeyboardButton> createButton(String text){
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(text)
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        return buttons;
    }
}

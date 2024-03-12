package com.example.please.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class NotificationButton {

    public static InlineKeyboardMarkup inlineButtonsForNotificationIfOn(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text("9:00").callbackData("9:0").build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text("8:00").callbackData("8:0").build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text("off").callbackData("off").build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button3);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        row.add(buttons1);
        row.add(buttons2);
        row.add(buttons3);

        markup.setKeyboard(row);

        return markup;

    }
}

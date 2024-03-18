package com.example.please.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton {

    public static InlineKeyboardMarkup inlineButtonsForSettings(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text("notification").callbackData("notification").build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text("fullname").callbackData("fullname").build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text("password").callbackData("password").build();
        InlineKeyboardButton button4 = InlineKeyboardButton.builder().text("room").callbackData("room").build();
        InlineKeyboardButton button5 = InlineKeyboardButton.builder().text("phonenumber").callbackData("phonenumber").build();
        InlineKeyboardButton button6 = InlineKeyboardButton.builder().text("status").callbackData("status").build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button3);

        List<InlineKeyboardButton> buttons4 = new ArrayList<>();
        buttons3.add(button4);

        List<InlineKeyboardButton> buttons5 = new ArrayList<>();
        buttons3.add(button5);

        List<InlineKeyboardButton> buttons6 = new ArrayList<>();
        buttons3.add(button6);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        row.add(buttons1);
        row.add(buttons2);
        row.add(buttons3);
        row.add(buttons4);
        row.add(buttons5);
        row.add(buttons6);

        markup.setKeyboard(row);

        return markup;

    }
}

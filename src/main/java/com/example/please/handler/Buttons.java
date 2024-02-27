package com.example.please.handler;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class Buttons {
    public static ReplyKeyboardMarkup getButtons(){

        KeyboardRow row = new KeyboardRow();
        row.add("На місці!");
        row.add("Список працівників");

        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboardRow(row)
                .build();

        return keyboardMarkup;
    }

}

package com.example.please.command;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class Buttons {

    private Buttons () {}
    public static ReplyKeyboardMarkup getButtons(){

        KeyboardRow row = new KeyboardRow();
        row.add("На місці!");
        row.add("Список працівників");

        return ReplyKeyboardMarkup
                .builder()
                .keyboardRow(row)
                .build();
    }

}

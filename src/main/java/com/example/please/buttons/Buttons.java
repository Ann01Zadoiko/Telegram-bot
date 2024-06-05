package com.example.please.buttons;

import com.example.please.constant.Commands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    private Buttons () {}

    public static ReplyKeyboardMarkup buttons(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(Commands.AT_WORK);
        row.add(Commands.LIST_OF_EMPLOYEES);

        keyboard.add(row);

        row = new KeyboardRow();

        row.add(Commands.NOTIFICATION);
        row.add(Commands.SETTINGS);

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

}

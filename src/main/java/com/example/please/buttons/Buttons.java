package com.example.please.buttons;


import com.example.please.constant.Commands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class Buttons {

    private Buttons () {}
    public static ReplyKeyboardMarkup getButtons(){

        KeyboardRow row = new KeyboardRow();
        row.add(Commands.AT_WORK);
        row.add(Commands.LIST_OF_EMPLOYEES);
        row.add(Commands.SETTINGS);

        return ReplyKeyboardMarkup
                .builder()
                .keyboardRow(row)
                .build();
    }

}

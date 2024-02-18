package com.example.please.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

//    public static ReplyKeyboardMarkup getKeyboard(){
//        //return new ReplyKeyboardMarkup(List.of(row));
//
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//
//        KeyboardRow row = new KeyboardRow();
//        row.add("На місці!");
//        row.add("Список працівників");
//        row.add("Keyboard converter");
//
//        keyboardRows.add(row);
//
//        keyboardMarkup.setKeyboard(keyboardRows);
//
//        return keyboardMarkup;
//    }

    public static ReplyKeyboardMarkup getButtons(){

        KeyboardRow row = new KeyboardRow();
        row.add("На місці!");
        row.add("Список працівників");

        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup
                .builder()
              //  .resizeKeyboard(true)
                .keyboardRow(row)
                .build();

        return keyboardMarkup;
    }

}

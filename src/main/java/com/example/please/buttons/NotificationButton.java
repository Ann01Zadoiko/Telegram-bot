package com.example.please.buttons;


import com.example.please.constant.Callback;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class NotificationButton {

    private NotificationButton(){}

    public static InlineKeyboardMarkup getButtonsIfTurnOnAtNine(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text(Callback.EIGHT).callbackData(Callback.EIGHT).build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text(Callback.TURN_OFF).callbackData(Callback.OFF).build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text(Callback.BACK).callbackData(Callback.BACK_TO_SETTINGS).build();

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

    public static InlineKeyboardMarkup getButtonsIfTurnOnAtEight(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text(Callback.NINE).callbackData(Callback.NINE).build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text(Callback.TURN_OFF).callbackData(Callback.OFF).build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text(Callback.BACK).callbackData(Callback.BACK_TO_SETTINGS).build();


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

    public static InlineKeyboardMarkup getButtonsIfTurnOff(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text(Callback.NINE).callbackData(Callback.NINE).build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text(Callback.EIGHT).callbackData(Callback.EIGHT).build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text(Callback.BACK).callbackData(Callback.BACK_TO_SETTINGS).build();

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

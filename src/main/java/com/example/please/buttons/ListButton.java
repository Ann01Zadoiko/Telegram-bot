package com.example.please.buttons;

import com.example.please.constant.Callback;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ListButton {

    private ListButton(){}

    //list of buttons (list of users (work, sick, vacation)
    public static InlineKeyboardMarkup getButtonsList(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 =InlineKeyboardButton
                .builder()
                .text("Списк тих, хто на работі")
                .callbackData(Callback.LIST_OF_WORK)
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text("Список тих, хто на лікарняному")
                .callbackData(Callback.LIST_OF_SICK)
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text("Список тих, хто у відпустці")
                .callbackData(Callback.LIST_OF_VACATION)
                .build();

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

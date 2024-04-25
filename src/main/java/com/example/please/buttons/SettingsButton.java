package com.example.please.buttons;

import com.example.please.constant.Settings;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton {

    private SettingsButton(){}


    //list of buttons for settings
    public static InlineKeyboardMarkup inlineButtonsForSettings(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(Settings.NOTIFICATION)
                .callbackData(Settings.NOTIFICATION)
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(Settings.FULL_NAME)
                .callbackData(Settings.FULL_NAME)
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text(Settings.PASSWORD)
                .callbackData(Settings.PASSWORD)
                .build();
        InlineKeyboardButton button4 = InlineKeyboardButton
                .builder()
                .text(Settings.ROOM)
                .callbackData(Settings.ROOM)
                .build();
        InlineKeyboardButton button5 = InlineKeyboardButton
                .builder()
                .text(Settings.PHONE_NUMBER)
                .callbackData(Settings.PHONE_NUMBER)
                .build();
        InlineKeyboardButton button6 = InlineKeyboardButton
                .builder()
                .text(Settings.STATUS)
                .callbackData(Settings.STATUS)
                .build();


        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);
        buttons1.add(button6);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);
        buttons2.add(button3);

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button4);
        buttons3.add(button5);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(buttons1);
        row.add(buttons2);
        row.add(buttons3);

        markup.setKeyboard(row);

        return markup;
    }

    //list of buttons with back button
    public static InlineKeyboardMarkup getButtons(String one, String two, String three){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(one)
                .callbackData(one)
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(two)
                .callbackData(two)
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text(three)
                .callbackData(three)
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

    public static InlineKeyboardMarkup getButtonsStatus(String one, String two, String three, String four){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(one)
                .callbackData(one)
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(two)
                .callbackData(two)
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text(three)
                .callbackData(three)
                .build();
        InlineKeyboardButton button4 = InlineKeyboardButton
                .builder()
                .text(four)
                .callbackData(four)
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);
       // buttons1.add(button2);
       // buttons1.add(button3);

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button3);

        List<InlineKeyboardButton> buttons4 = new ArrayList<>();
        buttons4.add(button4);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(buttons1);
        row.add(buttons2);
        row.add(buttons3);
        row.add(buttons4);

        markup.setKeyboard(row);

        return markup;
    }

}

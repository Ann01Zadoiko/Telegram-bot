package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.ListOfStatus;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;

@Slf4j
public class CallbackListOfStatus {

    public void getStatus(String data, User user, long chatId, long messageId, TelegramBot bot){

       if (data.equals(Settings.STATUS.getName())){

           Set<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
           InlineKeyboardSettingsButton<UserStatus> button = new InlineKeyboardSettingsButton<>();

           StatusEnum[] statusEnum = StatusEnum.values();
           ListOfStatus[] values = ListOfStatus.values();

           for (int i =  0; i < statusEnum.length - 1; i++){
                if (user.getStatusEnum().equals(statusEnum[i])){
                    enumSet.remove(UserStatus.values()[i]);
                    bot.executeEditMessageTextWithButton(values[i].getName(), chatId, messageId, button.getButtonsDifferentCount(enumSet));
                    log.info("Show the status for {}", user.getFullName());
                }
           }
       }
    }
}

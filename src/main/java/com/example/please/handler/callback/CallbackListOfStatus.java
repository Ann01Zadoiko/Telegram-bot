package com.example.please.handler.callback;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Settings;
import com.example.please.constant.UserStatus;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;

@Slf4j
public class CallbackListOfStatus {

    public void getStatus(String data, User user, long chatId, long messageId, TelegramBot bot){
        if (data.equals(Settings.STATUS.getName())){

            if (user.getStatusEnum().equals(StatusEnum.WORK)){
                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.WORK);
                bot.executeEditMessageTextWithButton("На даний момент Ви працюєте", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }

            if (user.getStatusEnum().equals(StatusEnum.SICK)){
                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.SICK);
                bot.executeEditMessageTextWithButton("На даний момент Ви на лікарняному", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }

            if (user.getStatusEnum().equals(StatusEnum.VACATION)){
                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.VACATION);
                bot.executeEditMessageTextWithButton("На даний момент Ви у відпустці", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }

            if (user.getStatusEnum().equals(StatusEnum.BUSINESS_TRIP)){
                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.BUSINESS_TRIP);
                bot.executeEditMessageTextWithButton("На даний момент Ви у відрядженні", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }

            if (user.getStatusEnum().equals(StatusEnum.REMOTE)){
                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.REMOTE);
                bot.executeEditMessageTextWithButton("На даний момент Ви на дистанційній роботі", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
            }

            log.info("Show the status for " + user.getFullName());
        }
    }
}

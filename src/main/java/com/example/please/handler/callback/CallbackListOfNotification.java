package com.example.please.handler.callback;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.Commands;
import com.example.please.constant.EnumNotification;
import com.example.please.constant.Phrases;
import com.example.please.notification.Notification;

import java.util.EnumSet;

public class CallbackListOfNotification {

//    public void getNotification(String data, Notification notification, long chatId, long messageId, TelegramBot bot){
//
//        if (data.equals(Commands.NOTIFICATION)){
//
//            if (notification.getTurnOn() && notification.getTimeOfNotification().equals("8:00")){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.EIGHT);
//
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8 ранку", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//
//            if (!notification.getTurnOn()){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.TURN_OFF);
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "вимкнено", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//
//            if (notification.getTurnOn() && notification.getTimeOfNotification().equals("8:40")){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.EIGHT_FORTY);
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:40", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//
//            if (notification.getTurnOn() && notification.getTimeOfNotification().equals("8:45")){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.EIGHT_FORTY_FIVE);
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:45", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//
//            if (notification.getTurnOn() && notification.getTimeOfNotification().equals("8:50")){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.EIGHT_FIFTY);
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:50", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//
//            if (notification.getTurnOn() && notification.getTimeOfNotification().equals("8:55")){
//                EnumSet<EnumNotification> enumSet = EnumSet.allOf(EnumNotification.class);
//                enumSet.remove(EnumNotification.EIGHT_FIFTY_FIVE);
//                bot.executeEditMessageTextWithButton(Phrases.NOTIFICATION + "у Вас о 8:55", chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
//            }
//        }
//    }
}

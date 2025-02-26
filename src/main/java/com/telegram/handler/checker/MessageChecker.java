package com.telegram.handler.checker;

import com.telegram.constant.Commands;
import com.telegram.constant.EnumNotification;
import com.telegram.constant.Settings;
import com.telegram.constant.UserStatus;
import com.telegram.notification.Notification;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;

public class MessageChecker {

    private MessageChecker(){}

    //check for full name (user enter full name)
    public static boolean isFullName(String [] stringBuilder, String messageText){
        return stringBuilder.length > 1 && stringBuilder.length < 4 && !(isACommand(messageText));
    }

    //check for unexpected message
    public static boolean isUnexpectedMessage(String messageText, String [] stringBuilder){
        return !isACommand(messageText) && !isPhoneNumber(messageText) && !isFullName(stringBuilder, messageText);
    }


    //check for commands
    public static boolean isACommand(String message){
        return message.equals(Commands.AT_WORK) || message.equals(Commands.START)
                 || message.equals(Commands.LIST_OF_EMPLOYEES) ||  message.equals(Commands.SETTINGS)
                || message.equals(Commands.START_PRIVATE) || message.equals(Commands.NOTIFICATION)
                || message.equals(Commands.STATUS);
    }

    //check for phone number (user enter phone number)
    public static boolean isPhoneNumber(String message){
        return message.startsWith("+380") || message.startsWith("0");
    }

    //check for notification
    public static boolean isNotificationAt(User user, Notification notification, String time){
        return (user.getAtWork() == 0 && notification.getTurnOn()
                && notification.getTimeOfNotification().equals(time) &&
                (user.getStatusEnum().equals(StatusEnum.WORK) || user.getStatusEnum().equals(StatusEnum.REMOTE)));
    }

    public static boolean isEnumValueNotification(String value) {
        for (EnumNotification e : EnumNotification.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnumValueStatus(String value) {
        for (UserStatus e : UserStatus.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnumValueSetting(String value) {
        for (Settings e : Settings.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

}

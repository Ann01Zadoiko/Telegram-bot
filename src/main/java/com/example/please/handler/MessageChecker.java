package com.example.please.handler;

import com.example.please.constant.Commands;
import com.example.please.notification.Notification;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;

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
}

package com.example.please.handler;

import com.example.please.constant.Commands;
import com.example.please.notification.Notification;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;

public class MessageChecker {

    private MessageChecker(){}

    //check for full name (user enter full name)
    public static boolean isFullName(String [] stringBuilder, String messageText){
        return stringBuilder.length > 1 && stringBuilder.length < 4 && !(isACommand(messageText)) && !isUnexpectedMessage(messageText);
    }

    //check for password (user enter password)
    public static boolean isPassword(String [] stringBuilder, String messageText){
        return !(isACommand(messageText)) && stringBuilder.length < 2 && isCyrillic(messageText) && messageText.toCharArray().length > 3;
    }

    //check for unexpected message
    public static boolean isUnexpectedMessage(String messageText){
        return !(isCyrillic(messageText.replaceAll("\\s","")))
                && !isACommand(messageText)
                && !isPhoneNumber(messageText)
                && !isDateOfBirth(messageText);
    }

    //check for cyrillic
    public static boolean isCyrillic(String iStringToCheck) {
        return iStringToCheck.matches("^[а-яґєіїА-ЯҐЄІЇ0-9.]+$");
    }

    //check for commands
    public static boolean isACommand(String message){

        return message.equals(Commands.AT_WORK) || message.equals(Commands.START)
                || message.equals(Commands.HELP) || message.equals(Commands.LIST_OF_EMPLOYEES)
              || message.contains(Commands.SEND) || message.equals(Commands.SETTINGS)
                || message.equals(Commands.START_PRIVATE);
    }


    //check for room (user enter number of room)
    public static boolean isARoom(String message){
        return message.toCharArray().length == 3;
    }

    //check for phone number (user enter phone number)
    public static boolean isPhoneNumber(String message){
        return message.startsWith("+380");
    }

    //check for notification
    public static boolean isNotificationAt(User user, Notification notification, String time){
        return (user.getAtWork() == 0 && notification.getTurnOn()
                && notification.getTimeOfNotification().contains(time)
                && user.getStatusEnum().equals(StatusEnum.WORK));
    }

    public static boolean isDateOfBirth(String message){
        return message.contains("-");
    }

}

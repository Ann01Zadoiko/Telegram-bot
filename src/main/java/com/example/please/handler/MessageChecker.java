package com.example.please.handler;

import com.example.please.constant.Commands;

public class MessageChecker {

    private MessageChecker(){}

    public static boolean isFullName(String [] stringBuilder, String messageText){
        return stringBuilder.length > 1 && stringBuilder.length < 4 && !(isACommand(messageText)) && !isUnexpectedMessage(messageText);
    }

    public static boolean isPassword(String [] stringBuilder, String messageText){
        return !(isACommand(messageText)) && stringBuilder.length < 2 && isCyrillic(messageText) && messageText.toCharArray().length > 3;
    }

    public static boolean isUnexpectedMessage(String messageText){
        return !(isCyrillic(messageText.replaceAll("\\s",""))) && !isACommand(messageText) && !isPhoneNumber(messageText);
    }

    public static boolean isCyrillic(String iStringToCheck) {
        return iStringToCheck.matches("^[а-яґєіїА-ЯҐЄІЇ0-9.]+$");
    }

    public static boolean isACommand(String message){

        if (message.equals(Commands.MY_PASSWORD) || message.equals(Commands.AT_WORK) ||
                message.equals(Commands.START) || message.equals(Commands.HELP) ||
                message.equals(Commands.LIST_OF_EMPLOYEES) || message.equals(Commands.MY_FULL_NAME) ||
                message.equals(Commands.ROOM) || message.equals(Commands.PHONE_NUMBER)
        || message.equals("/notification") || message.contains("/send")){
            return true;
        }

        return false;
    }

    public static boolean isARoom(String message){

        if (message.toCharArray().length == 3){
            return true;
        }

        return false;
    }

    public static boolean isPhoneNumber(String message){

        if (message.startsWith("+380")){
            return true;
        }

        return false;
    }

}

package com.example.please.check;

import com.example.please.constant.Commands;

public class MessageChecker {

    private MessageChecker(){}

    public static boolean isFullName(String [] stringBuilder, String messageText){
        return stringBuilder.length > 1 && !(isACommand(messageText)) && !isUnexpectedMessage(messageText);
    }

    public static boolean isPassword(String [] stringBuilder, String messageText){
        return !(isACommand(messageText)) && stringBuilder.length < 2 && isCyrillic(messageText);
    }

    public static boolean isUnexpectedMessage(String messageText){
        return !(isCyrillic(messageText.replaceAll("\\s",""))) && !isACommand(messageText);
    }

    public static boolean isCyrillic(final String iStringToCheck) {
        return iStringToCheck.matches("^[а-яґєіїА-ЯҐЄІЇ0-9.]+$");
    }

    public static boolean isACommand(String message){

        if ((message.equals(Commands.MY_PASSWORD) || message.equals(Commands.AT_WORK) ||
                message.equals(Commands.START) || message.equals(Commands.HELP) ||
                message.equals(Commands.LIST_OF_EMPLOYEES) || message.equals(Commands.MY_FULL_NAME))){
            return true;
        }

        return false;
    }

}

package com.telegram.handler.checker;

public class CheckerUnexpectedMessage {

    private CheckerUnexpectedMessage(){}

    public static boolean isUnexpectedMessage(String messageText, String [] stringBuilder){
        return !CheckerACommand.isACommand(messageText) &&
                !CheckerPhoneNumber.isPhoneNumber(messageText) &&
                !CheckerFullName.isFullName(stringBuilder, messageText);
    }
}

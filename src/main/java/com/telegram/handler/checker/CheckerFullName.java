package com.telegram.handler.checker;

public class CheckerFullName {

    private CheckerFullName(){}

    public static boolean isFullName(String [] stringBuilder, String messageText){
        return stringBuilder.length > 1 && stringBuilder.length < 4 && !(CheckerACommand.isACommand(messageText));
    }
}

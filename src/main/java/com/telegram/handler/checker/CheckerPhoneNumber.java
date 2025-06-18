package com.telegram.handler.checker;

public class CheckerPhoneNumber {

    private CheckerPhoneNumber(){}

    public static boolean isPhoneNumber(String message){
        return message.startsWith("+380") || message.startsWith("0");
    }
}

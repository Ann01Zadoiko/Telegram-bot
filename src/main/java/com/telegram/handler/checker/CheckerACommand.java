package com.telegram.handler.checker;

import com.telegram.constant.Commands;

public class CheckerACommand {

    private CheckerACommand(){}

    public static boolean isACommand(String message){
        return message.equals(Commands.AT_WORK) || message.equals(Commands.START)
                || message.equals(Commands.LIST_OF_EMPLOYEES) ||  message.equals(Commands.SETTINGS)
                || message.equals(Commands.START_PRIVATE) || message.equals(Commands.NOTIFICATION)
                || message.equals(Commands.STATUS);
    }
}

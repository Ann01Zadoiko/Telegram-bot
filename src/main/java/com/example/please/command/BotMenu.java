package com.example.please.command;

import java.util.ArrayList;
import java.util.List;

import com.example.please.constant.Commands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class BotMenu {

    public List<BotCommand> listOfCommands(){

        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand(Commands.HELP, "опис всіх команд"));
        list.add(new BotCommand(Commands.LIST_OF_SICK, "список тих, хто хвориє"));
        list.add(new BotCommand(Commands.LIST_OF_VACATION, "список тих, хто у відпусці"));
        return list;
    }
}

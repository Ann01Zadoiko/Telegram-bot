package com.example.please.command;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class BotMenu {

    public List<BotCommand> listOfCommands(){
        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand("/start", "get a welcome message"));
        list.add(new BotCommand("/help", "describe all commands"));
        list.add(new BotCommand("/mydata", "your data"));

        return list;
    }
}

package com.example.please.buttons;

import java.util.ArrayList;
import java.util.List;

import com.example.please.constant.Commands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class BotMenu {

    public List<BotCommand> listOfCommands(){

        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand(Commands.HELP, "описння всіх можливостій у боті"));
        return list;
    }
}

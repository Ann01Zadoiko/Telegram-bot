package com.example.please.command;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class BotMenu {

    public List<BotCommand> listOfCommands(){

        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand("/help", "опис всіх команд"));
        list.add(new BotCommand("/myfullname", "показує Віш ПІБ"));
        list.add(new BotCommand("/mypassword", "показує Ваш пароль"));

        return list;
    }
}

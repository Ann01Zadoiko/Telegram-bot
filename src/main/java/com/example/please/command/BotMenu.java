package com.example.please.command;

import java.util.ArrayList;
import java.util.List;

import com.example.please.constant.Commands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class BotMenu {

    public List<BotCommand> listOfCommands(){

        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand(Commands.HELP, "опис всіх команд"));
        list.add(new BotCommand(Commands.MY_FULL_NAME, "показує Віш ПІБ"));
        list.add(new BotCommand(Commands.MY_PASSWORD, "показує Ваш пароль"));
        list.add(new BotCommand(Commands.ROOM, "введення номеру кабінету, в якому працюєте"));
        list.add(new BotCommand(Commands.PHONE_NUMBER, "введення робочого номеру"));
        list.add(new BotCommand("/notification", "введення робочого номеру"));

        return list;
    }
}

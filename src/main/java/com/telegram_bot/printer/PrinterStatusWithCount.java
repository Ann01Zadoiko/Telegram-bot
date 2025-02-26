package com.telegram_bot.printer;

import com.telegram_bot.counter.CounterOfUsersForStatus;
import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;

import java.util.List;

public class PrinterStatusWithCount {

    public StringBuilder printCountAndList(List<User> users, StatusEnum statusEnum, String text){
        StringBuilder stringBuilder = new StringBuilder();
        CounterOfUsersForStatus counter = new CounterOfUsersForStatus();

        stringBuilder.append(text).append(": ").append(counter.countOfUsers(users, statusEnum)).append("\n");
        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum) && counter.countOfUsers(users, statusEnum) != 0){
                stringBuilder.append(user.getFullName()).append("\n");
            }
        }

        return stringBuilder;
    }
}

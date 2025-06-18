package com.telegram.printer;

import com.telegram.counter.CounterImp;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;

import java.util.List;

public class PrinterStatusWithCount {

    public StringBuilder printCountAndList(List<User> users, StatusEnum statusEnum, String text){
        StringBuilder stringBuilder = new StringBuilder();
        CounterImp counter = new CounterImp();

        stringBuilder.append(text).append(": ").append(counter.countOfUsers(users, statusEnum)).append("\n");
        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum) && counter.countOfUsers(users, statusEnum) != 0){
                stringBuilder.append(user.getFullName()).append("\n");
            }
        }

        return stringBuilder;
    }
}

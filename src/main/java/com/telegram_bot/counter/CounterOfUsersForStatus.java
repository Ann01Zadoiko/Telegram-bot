package com.telegram_bot.counter;

import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;

import java.util.List;

public class CounterOfUsersForStatus implements Counter{

    //count users for status
    @Override
    public int countOfUsers(List<User> users, StatusEnum statusEnum) {
        int count = 0;

        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum)){
                count++;
            }
        }

        return count;
    }
}

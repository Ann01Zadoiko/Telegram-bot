package com.telegram_bot.counter;

import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;

import java.util.List;

public interface Counter {

    int countOfUsers(List<User> users, StatusEnum statusEnum);
}

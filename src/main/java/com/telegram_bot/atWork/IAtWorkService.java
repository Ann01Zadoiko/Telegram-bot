package com.telegram_bot.atWork;

import com.telegram_bot.user.User;

import java.time.LocalTime;

public interface IAtWorkService {

    String addUserToTheList(User user, LocalTime time);
}

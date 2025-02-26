package com.telegram.counter;

import com.telegram.user.StatusEnum;
import com.telegram.user.User;

import java.util.List;

public interface Counter {

    int countOfUsers(List<User> users, StatusEnum statusEnum);
}

package com.example.please.counter;

import com.example.please.user.StatusEnum;
import com.example.please.user.User;

import java.util.List;

public interface Counter {

    int countOfUsers(List<User> users, StatusEnum statusEnum);
}

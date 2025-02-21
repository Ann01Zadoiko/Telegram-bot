package com.example.please.atWork;

import com.example.please.user.User;

import java.time.LocalTime;

public interface IAtWorkService {

    String addUserToTheList(User user, LocalTime time);
}

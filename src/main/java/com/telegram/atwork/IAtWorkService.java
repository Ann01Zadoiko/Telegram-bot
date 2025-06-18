package com.telegram.atwork;

import com.telegram.user.User;

import java.time.LocalTime;

public sealed interface IAtWorkService permits AtWorkService{

    String addUserToTheList(User user, LocalTime time);
}

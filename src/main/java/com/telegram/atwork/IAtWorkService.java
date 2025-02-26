package com.telegram.atwork;

import com.telegram.user.User;

import java.time.LocalTime;

public interface IAtWorkService {

    String addUserToTheList(User user, LocalTime time);
}

package com.example.please.atWork;


import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;


@Slf4j
@RequiredArgsConstructor
public class AtWorkService {

    private final UserService userService;

    public String atWorkClick(User user, LocalTime time){
        if (user.getAtWork() == 1){
            return "Двічі півторювати це не буду!";
        } else {
            user.setAtWork((byte) 1);
            user.setTimeComing(time);
            userService.update(user);
            return "Бажаю гарного робочого дня!";
        }
    }
}

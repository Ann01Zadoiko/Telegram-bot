package com.example.please.atWork;

import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalTime;


@Slf4j
@RequiredArgsConstructor
public class AtWorkService implements IAtWorkService{

    private final UserService userService;

    //user is added to the list
    @Override
    public String addUserToTheList(User user, LocalTime time){
        if (user.getAtWork() == 1){
            log.info("{} repeatedly pressed AT WORK", user.getFullName());
            return "Двічі півторювати це не буду!";

        } else {
            user.setAtWork((byte) 1);
            user.setTimeComing(time);
            userService.save(user);
            log.info("{} was added to the list", user.getFullName());
            return "Бажаю гарного робочого дня!";
        }
    }
}

package com.example.please.atWork;


import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
public class AtWorkService {

    private final UserService userService;

    public String atWorkClick(Long id, LocalTime time){

        User user = userService.getById(id);

        if (user.getAtWork() == 1){
            return "Двічі півторювати це не буду!";
        } else {
            user.setAtWork((byte) 1);
            user.setTimeComing(time);
            userService.update(user);
            return "Бажаю гарного робочого дня!";
        }
    }

    public String print( ){
        String list;

        List<User> users1 = userService.listAll();

        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        list = formatterDay.format(LocalDate.now());

        for (User user: users1){
            if (user.getAtWork() == 1 && user.getStatus().equals(Status.WORK)){
               list += "\n" + (number++) + ". " + user.getFullName() + " (" + formatterTime.format(user.getTimeComing()) + ")";
            }
        }
        return list;
    }

}

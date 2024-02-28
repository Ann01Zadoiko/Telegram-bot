package com.example.please.atWork;


import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AtWorkService {

    private final UserService userService;

    public void atWorkClick(Long chatId, LocalDate date){

        User user = userService.getByChatId(chatId);

        if (date.equals(LocalDate.now())){
            user.setAtWork(true);
            userService.update(user);

            log.info("User (" + user.getFullName() + ") is at work");
        } else {
            user.setAtWork(false);
            userService.update(user);

            log.info("User (" + user.getFullName() + ") is ALREADY at work");
        }

    }

    public StringBuilder print(LocalDate date){

        List<User> users1 = userService.listAll();

        int number = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        StringBuilder list = new StringBuilder(formatter.format(LocalDate.now()));

        for (User user: users1){
            if (date.equals(LocalDate.now()) && user.isAtWork()){
                list.append("\n").append(number++).append(". ").append(user.getFullName());
            }
        }

        return list;
    }

}

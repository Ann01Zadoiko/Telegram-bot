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

    public String atWorkClick(Long chatId){

        User user = userService.getByChatId(chatId);

        if (user.isAtWork()){
            return "Двічі півторювати це не буду!";
        } else {
            user.setAtWork(true);
            userService.update(user);
            return "Бажаю гарного робочого дня!";
        }

    }

    public String print(String list){

        List<User> users1 = userService.listAll();

        int number = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        list = formatter.format(LocalDate.now());

        if (users1.isEmpty()){
            return "Ніхто не прийшов на роботу";
        }

        for (User user: users1){
            if (user.isAtWork()){
               list += "\n" + (number++) + ". " + user.getFullName();
                log.info(user.getFullName() + ": " + user.isAtWork());
            }
        }

        return list;
    }

}

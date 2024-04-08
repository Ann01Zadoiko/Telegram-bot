package com.example.please.atWork;

import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListOfEmployees {

    private final UserService userService;

    public String printAllUsers(){
        StringBuilder list = new StringBuilder();

        List<User> users = userService.listAll();

        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        list.append(formatterDay.format(LocalDate.now()));

        list.append("\nНа роботі:");
        for (User user: users){
            if (user.getAtWork() == 1 && user.getStatusEnum().equals(StatusEnum.WORK)){
                list
                        .append("\n")
                        .append(number++)
                        .append(". ")
                        .append(user.getFullName())
                        .append(" (")
                        .append(formatterTime.format(user.getTimeComing()))
                        .append(")");
            }
        }

        list.append("\nНа лікарняному:");
        for (User user: users){
            if (user.getStatusEnum().equals(StatusEnum.SICK)){
                list
                        .append("\n")
                        .append(number++)
                        .append(user.getFullName());
            }
        }

        list.append("\nУ відпустці:");
        for (User user: users){
            if (user.getStatusEnum().equals(StatusEnum.VACATION)){
                list
                        .append("\n")
                        .append(number++)
                        .append(user.getFullName());
            }
        }
        return String.valueOf(list);
    }



}

package com.example.please.atWork;

import com.example.please.user.Status;
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

    //print users who sick or vacation
    public String printEmployees(Status status){
        List<User> users = userService.listAll();

        StringBuilder list = new StringBuilder();
        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        list.append(formatterDay.format(LocalDate.now()));

        for (User user: users){
            if (user.getStatus().equals(status)){
                list
                        .append("\n")
                        .append(number++)
                        .append(". ")
                        .append(user.getFullName());
            }
        }

        log.info("List of users (" + status + "): " + list);
        return String.valueOf(list);
    }

    ///print users who work
    public String printEmployeeWork( ){
        StringBuilder list = new StringBuilder();

        List<User> users1 = userService.listAll();

        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        list.append(formatterDay.format(LocalDate.now()));

        for (User user: users1){
            if (user.getAtWork() == 1 && user.getStatus().equals(Status.WORK)){
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

        log.info("List of user (work): " + list);
        return String.valueOf(list);
    }

}

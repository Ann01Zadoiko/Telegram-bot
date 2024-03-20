package com.example.please.atWork;

import com.example.please.user.Status;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class ListOfEmployees {

    private final UserService userService;

    public String printEmployeesSick(){
        List<User> users = userService.listAll();

        StringBuilder list = new StringBuilder();

        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        //String list = formatterDay.format(LocalDate.now());
        list.append(formatterDay.format(LocalDate.now()));

        for (User user: users){
            if (user.getStatus().equals(Status.SICK)){
                list.append("\n").append(number).append(". ").append(user.getFullName());
            }
        }

        return String.valueOf(list);
    }
    public String printEmployeesVacation(){
        List<User> users = userService.listAll();

        StringBuilder list = new StringBuilder();

        int number = 1;
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        //String list = formatterDay.format(LocalDate.now());
        list.append(formatterDay.format(LocalDate.now()));

        for (User user: users){
            if (user.getStatus().equals(Status.VACATION)){
                list.append("\n").append(number).append(". ").append(user.getFullName());
            }
        }

        return String.valueOf(list);
    }

}

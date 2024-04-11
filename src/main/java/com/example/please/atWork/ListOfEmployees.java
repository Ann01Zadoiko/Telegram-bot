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

    //print all users of the day
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

        list.append(printList(StatusEnum.SICK, "На лікарняному:", users, countUsers(StatusEnum.SICK, users)));
        list.append(printList(StatusEnum.VACATION, "У відпустці:", users, countUsers(StatusEnum.VACATION, users)));

        log.info(list.toString());

        return String.valueOf(list);
    }

    //count users
    private int countUsers(StatusEnum statusEnum, List<User> users){
        int count = 0;

        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum)){
                count++;
            }
        }
        return count;
    }

    //print list of user (entered status)
    private StringBuilder printList(StatusEnum statusEnum, String text, List<User> users, int count){
        StringBuilder list = new StringBuilder();
        int number = 1;

        if (count == 0){
            return list;
        }

        list
                .append("\n\n")
                .append(text);

        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum)){
                list
                        .append("\n")
                        .append(number++)
                        .append(". ")
                        .append(user.getFullName());
            }
        }

        return list;
    }

}

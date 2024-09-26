package com.example.please.printer;

import com.example.please.counter.CounterOfUsersForList;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PrinterListOfEmployeesByStatus {

    private final UserService userService;

    public StringBuilder printListByTheStatus(StatusEnum statusEnum, String text, List<User> users){
        StringBuilder list = new StringBuilder();
        CounterOfUsersForList counter = new CounterOfUsersForList();

        int number = 1;
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        if (counter.countOfUsers(users, statusEnum) == 0){
            return list;
        }

        list
                .append("\n\n")
                .append(text);

        for (User user: users){
            if ((user.getStatusEnum().equals(statusEnum))){
                list
                        .append("\n")
                        .append(number++)
                        .append(". ")
                        .append(user.getFullName());

                if ( (user.getStatusEnum().equals(StatusEnum.REMOTE) || user.getStatusEnum().equals(StatusEnum.WORK))
                        && user.getAtWork() == 1){
                    list.append(" (")
                            .append(formatterTime.format(user.getTimeComing()))
                            .append(")");
                }
            }

        }
        return list;
    }
}

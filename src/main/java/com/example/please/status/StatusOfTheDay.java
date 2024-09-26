package com.example.please.status;

import com.example.please.counter.CounterOfUsersForStatus;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatusOfTheDay {

    public String printStatus(LocalDate date, List<User> users){
        StringBuilder status = new StringBuilder();
        CounterOfUsersForStatus counter = new CounterOfUsersForStatus();

        int countOfUsersAreNotAtWork = users.size() -
                counter.countOfUsers(users, StatusEnum.WORK) -
                counter.countOfUsers(users, StatusEnum.REMOTE);

        int countOfUsersAreAtWork =
                counter.countOfUsers(users, StatusEnum.WORK) +
                counter.countOfUsers(users, StatusEnum.REMOTE);

        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        status.append("КП ОІАЦ станом на ").append(formatterDay.format(date)).append("\n")
                .append("По штату: 33;").append("\n")
                .append("Некомплект: ").append(33-users.size()).append(";\n\n")
                .append("По списку всього: ").append(users.size()).append(";\n")
                .append("ПРИСУТНІ: ").append(countOfUsersAreAtWork).append(";\n")
                .append("ВІДСУТНІ: ").append(countOfUsersAreNotAtWork).append(";\n")
                .append("З них:\n")
                .append(printCountAndList(users, StatusEnum.SICK, "Лікарняний"))
                .append(printCountAndList(users, StatusEnum.VACATION, "Відпуска"))
                .append(printCountAndList(users, StatusEnum.BUSINESS_TRIP, "Відряждення"))
                .append("Інше:\n")
                .append(printCountAndList(users, StatusEnum.REMOTE, "Віддалена робота"));

        return String.valueOf(status);
    }

    private StringBuilder printCountAndList(List<User> users, StatusEnum statusEnum, String text){
        StringBuilder stringBuilder = new StringBuilder();
        CounterOfUsersForStatus counter = new CounterOfUsersForStatus();

        stringBuilder.append(text).append(": ").append(counter.countOfUsers(users, statusEnum)).append("\n");
        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum) && counter.countOfUsers(users, statusEnum) != 0){
                stringBuilder.append(user.getFullName()).append("\n");
            }
        }

        return stringBuilder;
    }
}

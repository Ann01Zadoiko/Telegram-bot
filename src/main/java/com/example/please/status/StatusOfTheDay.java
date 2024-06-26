package com.example.please.status;

import com.example.please.user.StatusEnum;
import com.example.please.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatusOfTheDay {

    public String printStatus(LocalDate date, List<User> users){
        StringBuilder status = new StringBuilder();

        int countOfUsersAreNotAtWork = users.size() -
                countOfUsersWithStatus(users, StatusEnum.WORK) -
                countOfUsersWithStatus(users, StatusEnum.REMOTE);

        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        status.append("КП ОІАЦ станом на ").append(formatterDay.format(date)).append("\n")
                .append("По штату: 33;")
                .append("\n").append("Некомплект: ").append(33-users.size())
                .append(";\n\n").append("По списку всього: ").append(users.size())
                .append(";\n")
                .append("ПРИСУТНІ: ").append(countOfUsersWithStatus(users, StatusEnum.WORK)).append(";\n")
                .append("ВІДСУТНІ: ").append(countOfUsersAreNotAtWork).append(";\n")
                .append("З них:\n").append(printCountAndList(users, StatusEnum.SICK, "Лікарняний"))
                .append(printCountAndList(users, StatusEnum.VACATION, "Відпуска"))
                .append(printCountAndList(users, StatusEnum.BUSINESS_TRIP, "Відряждення"))
                .append("Інше:\n")
                .append(printCountAndList(users, StatusEnum.REMOTE, "Віддалена робота"));

        return String.valueOf(status);
    }

    private int countOfUsersWithStatus(List<User> users, StatusEnum statusEnum){
        int count = 0;

        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum)){
                count++;
            }
        }

        return count;
    }

    private StringBuilder printCountAndList(List<User> users, StatusEnum statusEnum, String text){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(text).append(": ").append(countOfUsersWithStatus(users, statusEnum)).append("\n");
        for (User user: users){
            if (user.getStatusEnum().equals(statusEnum) && countOfUsersWithStatus(users, statusEnum) != 0){
                stringBuilder.append(user.getFullName()).append("\n");
            }
        }

        return stringBuilder;
    }
}

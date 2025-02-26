package com.telegram_bot.printer;

import com.telegram_bot.counter.CounterOfUsersForStatus;
import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrinterByStatus {

    public String printStatus(LocalDate date, List<User> users){
        StringBuilder status = new StringBuilder();
        CounterOfUsersForStatus counter = new CounterOfUsersForStatus();
        PrinterStatusWithCount printer = new PrinterStatusWithCount();

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
                .append(printer.printCountAndList(users, StatusEnum.SICK, "Лікарняний"))
                .append(printer.printCountAndList(users, StatusEnum.VACATION, "Відпуска"))
                .append(printer.printCountAndList(users, StatusEnum.BUSINESS_TRIP, "Відряждення"))
                .append("Інше:\n")
                .append(printer.printCountAndList(users, StatusEnum.REMOTE, "Віддалена робота"));

        return String.valueOf(status);
    }
}

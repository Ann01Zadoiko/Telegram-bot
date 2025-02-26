package com.telegram_bot.printer;

import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;
import com.telegram_bot.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PrinterListOfEmployees {

    private final UserService userService;

    public String showAllUsersOfTheDay(){
        StringBuilder list = new StringBuilder();
        PrinterListOfEmployeesByStatus printer = new PrinterListOfEmployeesByStatus();

        List<User> users = userService.listAll();

        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        list.append(formatterDay.format(LocalDate.now()));

        list.append(printer.printListByTheStatus(StatusEnum.WORK, "На роботі:", users));
        list.append(printer.printListByTheStatus(StatusEnum.REMOTE, "На дистанційній роботі:", users));
        list.append(printer.printListByTheStatus(StatusEnum.SICK, "На лікарняному:", users));
        list.append(printer.printListByTheStatus(StatusEnum.VACATION, "У відпустці:", users));
        list.append(printer.printListByTheStatus(StatusEnum.BUSINESS_TRIP, "У відряженні:", users));

        return String.valueOf(list);
    }
}

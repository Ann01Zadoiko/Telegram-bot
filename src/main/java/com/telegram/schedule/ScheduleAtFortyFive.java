package com.telegram.schedule;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleAtFortyFive implements ScheduleDailyMessage{

    private final ListOfUsersForSchedule list;

    @SneakyThrows
    @Scheduled(cron = "0 45 8 * * MON-FRI")
    @Override
    public void dailyRemember() {
        list.listOfUserForSchedule("8:45");
    }
}

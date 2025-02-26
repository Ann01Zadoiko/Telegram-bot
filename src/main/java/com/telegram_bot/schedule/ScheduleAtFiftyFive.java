package com.telegram_bot.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleAtFiftyFive implements ScheduleDailyMessage{

    private final ListOfUsersForSchedule list;


    @Scheduled(cron = "0 55 8 * * MON-FRI")
    @Override
    public void dailyRemember() {
        list.listOfUserForSchedule("8:55");
    }
}

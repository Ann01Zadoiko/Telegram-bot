package com.telegram_bot.schedule;


import com.telegram_bot.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleForNewDay implements ScheduleDailyMessage{

    private final UserService userService;

    //reset the list
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void dailyRemember() {
        userService.setNewDay();
        log.info("A new day!");
    }

}

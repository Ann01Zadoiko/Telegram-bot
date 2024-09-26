package com.example.please.schedule;

import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleForNewDay implements ScheduleDailyMessage{

    private final UserService userService;

    //reset the list
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void dailyRemember() {
        List<User> users = userService.listAll();
        log.info("A new day!");

        for (User user : users) {
            user.setAtWork((byte) 0);
            userService.save(user);
        }
    }
}

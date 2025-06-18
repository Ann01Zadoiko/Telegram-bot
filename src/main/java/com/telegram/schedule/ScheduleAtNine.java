package com.telegram.schedule;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Phrases;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleAtNine implements ScheduleDailyMessage{

    private final UserService service;
    private final TelegramBot bot;

    @SneakyThrows
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    @Override
    public void dailyRemember() {
        List<User> users = service.listOfUsesAreNotAtWOrk();
        for (User user: users){
                bot.sendMessage(user.getChatId(), Phrases.LATE);
        }
    }
}

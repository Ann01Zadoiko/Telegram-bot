package com.telegram.schedule;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Phrases;
import com.telegram.notification.Notification;
import com.telegram.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ReminderService {

    private final NotificationService notificationService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final TelegramBot telegramBot;

    public void scheduleAllReminders() {
        List<Notification> notifications = notificationService.findAll();
        for (Notification notification : notifications) {
            scheduleReminder(notification);
        }
    }

    public void scheduleReminder(Notification notification) {

        String[] split = notification.getTimeOfNotification().split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(hour)
                .withMinute(minute)
                .withSecond(0);

        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }

        while (nextRun.getDayOfWeek() == DayOfWeek.SATURDAY || nextRun.getDayOfWeek() == DayOfWeek.SUNDAY) {
            nextRun = nextRun.plusDays(1);
        }

        long delay = Duration.between(now, nextRun).toMillis();

        taskScheduler.schedule(() -> {
            try {
                telegramBot.sendMessage(notification.getUser().getChatId(), Phrases.REMEMBER);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            scheduleReminder(notification);
        }, new java.util.Date(System.currentTimeMillis() + delay));
    }

}

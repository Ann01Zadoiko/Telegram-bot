package com.telegram_bot.handler.callback;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.buttons.SettingsButton;
import com.telegram_bot.constant.Phrases;
import com.telegram_bot.constant.Steps;
import com.telegram_bot.constant.UserStatus;
import com.telegram_bot.user.StatusEnum;
import com.telegram_bot.user.User;
import com.telegram_bot.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.EnumSet;

@RequiredArgsConstructor
@Slf4j
public class CallbackStatus {

    private final UserService userService;

    @SneakyThrows
    public void commandsForStatuses(String data, User user, long chatId, long messageId, TelegramBot bot){

        EnumSet<UserStatus> enumSet = EnumSet.of(UserStatus.BACK);
        UserStatus[] userStatuses = UserStatus.values();
        StatusEnum[] statusEnums = StatusEnum.values();

        for (int i = 0; i < userStatuses.length-1; i++) {
            if (data.equals(userStatuses[i].getName()) && !(data.equals(UserStatus.BACK.getName()))){
                if (user.getStatusEnum().equals(StatusEnum.NOTHING)){
                    user.setStatusEnum(statusEnums[i]);
                    userService.save(user);
                    bot.execute(EditMessageText
                            .builder()
                            .chatId(chatId)
                            .messageId((int) messageId)
                            .text(Steps.THE_END)
                            .build());
                    bot.sendMessage(chatId, Steps.STEP_3);
                    log.info("User ({}) change the status to {}", chatId, data);
                } else {
                    user.setStatusEnum(statusEnums[i]);
                    userService.save(user);
                    bot.executeEditMessageTextWithButton(Phrases.STATUS + userStatuses[i].getName().toLowerCase(), chatId, messageId, new SettingsButton().getButtonsDifferentCount(enumSet));
                    log.info("User ({}) change the status to {}", chatId, data);
                }
            }
        }
    }
}

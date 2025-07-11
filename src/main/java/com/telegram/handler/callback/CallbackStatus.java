package com.telegram.handler.callback;

import com.telegram.bot.TelegramBot;
import com.telegram.buttons.inline.InlineKeyboardSettingsButton;
import com.telegram.constant.Phrases;
import com.telegram.constant.Steps;
import com.telegram.constant.UserStatus;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class CallbackStatus {

    private final UserService userService;

    @SneakyThrows
    public void commandsForStatuses(String data, User user, long chatId, long messageId, TelegramBot bot){

        Set<UserStatus> enumSet = EnumSet.of(UserStatus.BACK);
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
                } else {
                    user.setStatusEnum(statusEnums[i]);
                    userService.save(user);
                    String text = Phrases.STATUS + userStatuses[i].getName().toLowerCase();
                    InlineKeyboardSettingsButton<UserStatus> button = new InlineKeyboardSettingsButton<>();
                    bot.executeEditMessageTextWithButton(text, chatId, messageId, button.getButtonsDifferentCount(enumSet));
                }
                log.info("User ({}) change the status to {}", chatId, data);
            }

        }
    }
}

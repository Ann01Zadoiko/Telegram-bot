package com.telegram.handler.message;

import com.telegram.atwork.AtWorkService;
import com.telegram.bot.TelegramBot;
import com.telegram.constant.Commands;
import com.telegram.user.StatusEnum;
import com.telegram.user.User;
import com.telegram.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalTime;


@Slf4j
@RequiredArgsConstructor
public class MessageForAtWork {

    private final UserService userService;

    @SneakyThrows
    public void commandForMessage(String messageText, TelegramBot bot, long chatId, User user){
        if (messageText.equals(Commands.AT_WORK)){
            String s = new AtWorkService(userService).addUserToTheList(user, LocalTime.now());
            if(user.getStatusEnum().equals(StatusEnum.WORK) || user.getStatusEnum().equals(StatusEnum.REMOTE)){
                log.info("User ({}) is at work", chatId);
                bot.sendMessage(chatId, s);
            } else {
                bot.sendMessage(chatId, "Ти не маєшь права натискати на цю кнопку!");
            }
        }
    }
}

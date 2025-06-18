package com.telegram.handler.message;

import com.telegram.bot.TelegramBot;
import com.telegram.constant.Phrases;
import com.telegram.handler.checker.CheckerUnexpectedMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageForUnexpectedMessage {

    @SneakyThrows
    public void getUnexpectedMessage(String messageText, String [] stringBuilder, long charId, TelegramBot bot){
        if (CheckerUnexpectedMessage.isUnexpectedMessage(messageText, stringBuilder)) {
            bot.sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            log.info("User ({}) entered incorrect message", charId);
        }
    }
}

package com.telegram_bot.handler.message;

import com.telegram_bot.bot.TelegramBot;
import com.telegram_bot.constant.Phrases;
import com.telegram_bot.handler.MessageChecker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageForUnexpectedMessage {

    @SneakyThrows
    public void getUnexpectedMessage(String messageText, String [] stringBuilder, long charId, TelegramBot bot){
        if (MessageChecker.isUnexpectedMessage(messageText, stringBuilder)) {
            bot.sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            log.info("User ({}) entered incorrect message", charId);
        }
    }
}

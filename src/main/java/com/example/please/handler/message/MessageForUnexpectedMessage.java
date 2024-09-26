package com.example.please.handler.message;

import com.example.please.bot.TelegramBot;
import com.example.please.constant.Phrases;
import com.example.please.handler.MessageChecker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageForUnexpectedMessage {

    @SneakyThrows
    public void getUnexpectedMessage(String messageText, String [] stringBuilder, long charId, TelegramBot bot){
        if (MessageChecker.isUnexpectedMessage(messageText, stringBuilder)) {
            bot.sendMessage(charId, Phrases.UNEXPECTED_MESSAGE);
            log.info("User (" + charId + ") entered incorrect message");
        }
    }
}

package com.telegram_bot.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@Data
@Configuration
@EnableScheduling
@ComponentScan("com.example.please")
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

}

package com.example.please.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@Configuration
@EnableScheduling
//@EnableJpaRepositories(basePackages = "com.example.please")
@ComponentScan("com.example.please")
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.chat_id}")
    String botOwner;

//    @Bean
//    public NotificationService notificationService(){
//        return new NotificationService();
//    }



}

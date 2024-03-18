package com.example.please.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableJpaRepositories(basePackages = "com.example.please")
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


//    @Bean
//    public NotificationService notificationService(){
//        return new NotificationService();
//    }



}

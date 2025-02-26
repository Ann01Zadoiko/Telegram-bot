package com.telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.please.user",
		"com.example.please.schedule",
		"com.example.please.notification",
		"com.example.please.atWork",
		"com.example.please.config"})
public class PleaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PleaseApplication.class, args);
	}

}

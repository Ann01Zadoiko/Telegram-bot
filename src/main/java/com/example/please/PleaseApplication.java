package com.example.please;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.please")
//@EnableJpaRepositories("com.example.please.notification")
public class PleaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PleaseApplication.class, args);
	}

}

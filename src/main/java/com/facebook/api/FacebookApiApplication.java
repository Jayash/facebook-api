package com.facebook.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FacebookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookApiApplication.class, args);
	}

}

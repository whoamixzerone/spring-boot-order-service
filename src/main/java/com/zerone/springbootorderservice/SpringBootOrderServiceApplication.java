package com.zerone.springbootorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOrderServiceApplication.class, args);
	}

}

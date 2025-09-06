package com.sena.JustMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.sena.JustMe.repository")
@EntityScan(basePackages = "com.sena.JustMe.model")
@SpringBootApplication 
public class JustmeApplication  {

	public static void main(String[] args) {
		SpringApplication.run(JustmeApplication.class, args);
	}

}
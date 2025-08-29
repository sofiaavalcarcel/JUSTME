package com.sena.JustMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
 
@SpringBootApplication (exclude = DataSourceAutoConfiguration.class)
public class JustmeApplication  {

	public static void main(String[] args) {
		SpringApplication.run(JustmeApplication.class, args);
	}

}
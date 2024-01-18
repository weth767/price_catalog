package com.jpsouza.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClassifierServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassifierServiceApplication.class, args);
	}
}

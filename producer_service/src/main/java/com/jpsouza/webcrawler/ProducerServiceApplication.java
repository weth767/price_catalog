package com.jpsouza.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableFeignClients
public class ProducerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProducerServiceApplication.class, args);
	}
}

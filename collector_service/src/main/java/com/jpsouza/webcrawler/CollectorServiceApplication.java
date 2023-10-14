package com.jpsouza.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CollectorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectorServiceApplication.class, args);
    }
}

package com.jpsouza.webcrawler.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.GsonBuilder;
import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.services.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductsTopicListener {
    @Value("${spring.kafka.topics.products}")
    private String topic;
    private final ProductService productService;

    @KafkaListener(topics = "${spring.kafka.topics.products}", groupId = "webcrawler")
    public void consume(ConsumerRecord<String, String> payload) {
        log.info("product: {}", payload.value());
        productService.getProduct(new GsonBuilder().setLenient().setPrettyPrinting().disableHtmlEscaping().create()
                .fromJson(payload.value(), ProductDTO.class));
    }
}

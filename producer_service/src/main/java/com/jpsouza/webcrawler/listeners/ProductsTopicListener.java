package com.jpsouza.webcrawler.listeners;

import com.jpsouza.webcrawler.services.LinkProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductsTopicListener {
    @Value("${spring.kafka.topics.links}")
    private String topic;
    private final LinkProductService productService;

    @KafkaListener(topics = "${spring.kafka.topics.links}", groupId = "webcrawler")
    public void consume(ConsumerRecord<String, String> payload) {
        log.info("link: {}", payload.value());
        productService.verifyProductPageConditions(payload.value());
    }
}

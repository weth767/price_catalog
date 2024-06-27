package com.jpsouza.webcrawler.kafka;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger LOGGER = LogManager.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topics.products}")
    private String topic;

    public void sendMessage(String message) {
        LOGGER.info(String.format("Produto enviado: %s", message));
        kafkaTemplate.send(topic, message);
    }
}

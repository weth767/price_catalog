package com.jpsouza.webcrawler.kafka;

import com.jpsouza.webcrawler.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger LOGGER = LogManager.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        LOGGER.info(String.format("Message sent -> %s", message));
        kafkaTemplate.send(Constants.TOPIC_NAME, message);
    }
}

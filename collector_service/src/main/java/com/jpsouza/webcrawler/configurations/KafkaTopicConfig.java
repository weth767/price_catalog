package com.jpsouza.webcrawler.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topics.products}")
    private String topic;

    @Bean
    public NewTopic createNewTopic() {
        return TopicBuilder.name(topic)
                .build();
    }
}
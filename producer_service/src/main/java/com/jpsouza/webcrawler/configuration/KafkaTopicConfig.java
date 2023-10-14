package com.jpsouza.webcrawler.configuration;


import com.jpsouza.webcrawler.utils.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createNewTopic(){
        return TopicBuilder.name(Constants.TOPIC_NAME)
                .build();
    }
}

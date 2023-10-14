package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class TesteController {
    private final KafkaProducer producer;

    @PostMapping
    public ResponseEntity<String> sendKafkaMessage(@RequestBody String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Mensagem enviada com sucesso");
    }
}

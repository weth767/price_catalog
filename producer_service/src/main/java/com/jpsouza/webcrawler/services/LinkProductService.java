package com.jpsouza.webcrawler.services;

import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.kafka.KafkaProducer;
import com.jpsouza.webcrawler.runnables.ProductPageRunnable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkProductService {
    private final KafkaProducer kafkaProducer;
    private Thread thread;

    /**
     * Método que vai verificar quando é uma página de produto e filtrar as
     * informações desse produto para salvar na
     * nova tabela
     */
    public void verifyProductPageConditions(String url) {
        thread = new Thread(new ProductPageRunnable(url, kafkaProducer));
        thread.start();
    }
}

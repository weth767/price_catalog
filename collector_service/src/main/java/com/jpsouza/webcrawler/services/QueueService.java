package com.jpsouza.webcrawler.services;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.jpsouza.webcrawler.models.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueueService {
    private static QueueService instance;
    private ConcurrentLinkedQueue<Domain> queue = new ConcurrentLinkedQueue<>();

    private QueueService() {
    }

    public static QueueService getInstance() {
        if (instance == null) {
            instance = new QueueService();
        }
        return instance;
    }
}

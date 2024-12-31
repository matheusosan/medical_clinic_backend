package com.spring_app.demo.services.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService implements MessagingService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
            System.out.println("Enviou!");

        } catch (RuntimeException e) {
            System.out.println(e.getStackTrace());
        }
    }
}

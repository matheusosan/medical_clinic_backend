package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.EmailPayloadDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService implements IMessagingService {
    private final KafkaTemplate<String, EmailPayloadDTO> kafkaTemplate;

    public KafkaMessageService(KafkaTemplate<String, EmailPayloadDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, EmailPayloadDTO message) {
        try {
            kafkaTemplate.send(topic, message);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}

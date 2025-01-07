package com.spring_app.demo.services.MessagingService;

import com.spring_app.demo.dtos.EmailPayloadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService implements MessagingService {

    @Autowired
    private KafkaTemplate<String, EmailPayloadDTO> kafkaTemplate;

    @Override
    public void sendMessage(String topic, EmailPayloadDTO message) {
        try {
            kafkaTemplate.send(topic, message);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}

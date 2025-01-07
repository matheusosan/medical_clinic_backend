package com.spring_app.demo.services.MessagingService;

import com.spring_app.demo.dtos.EmailPayloadDTO;

public interface MessagingService {

    void sendMessage(String topic, EmailPayloadDTO message);
}

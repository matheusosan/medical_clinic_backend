package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.EmailPayloadDTO;

public interface IMessagingService {
    void sendMessage(String topic, EmailPayloadDTO message);
}

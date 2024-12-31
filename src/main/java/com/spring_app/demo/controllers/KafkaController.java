package com.spring_app.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostMapping()
    void testKafka() {

        try {
            kafkaTemplate.send("email_topic", "teste_de_mensagem");
            System.out.println("Enviou!");

        } catch (RuntimeException e) {
            System.out.println(e.getStackTrace());
        }
    };
}

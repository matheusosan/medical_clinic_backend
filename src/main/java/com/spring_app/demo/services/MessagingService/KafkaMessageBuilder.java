package com.spring_app.demo.services.MessagingService;

import com.spring_app.demo.entities.Client;
import com.spring_app.demo.entities.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class KafkaMessageBuilder {
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    public static Map<String, Object> buildAppointmentMessage(Client client, Service service, Instant appointmentInstant) {
        ZonedDateTime appointmentDateTime = appointmentInstant.atZone(DEFAULT_ZONE);

        Map<String, Object> emailPayload = new HashMap<>();
        emailPayload.put("clientName", client.getName());
        emailPayload.put("serviceName", service.getName());
        emailPayload.put("appointmentDateTime", appointmentDateTime.toString());
        return emailPayload;
    }
}

package com.profile.userProfileManagement.RabbitMQ;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.userProfileManagement.dtos.requests.rabbitMQNotificationDto;
import com.profile.userProfileManagement.service.RabbitMQConsumerService;


@Component
public class RabbitMQNotificationReceiver {

    @Autowired
    private RabbitMQConsumerService rabbitService;

    @Autowired
    private ObjectMapper objectMapper;

    public void receiveMessage(LinkedHashMap<String, Object> message) {
        System.out.println("Received message: " + message);

        rabbitMQNotificationDto dto = objectMapper.convertValue(message, rabbitMQNotificationDto.class);

        rabbitService.processNotification(dto);

    }

}

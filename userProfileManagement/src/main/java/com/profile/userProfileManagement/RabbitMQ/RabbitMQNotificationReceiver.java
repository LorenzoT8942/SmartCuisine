package com.profile.userProfileManagement.RabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.profile.userProfileManagement.dtos.requests.rabbitMQNotificationDto;
import com.profile.userProfileManagement.service.RabbitMQConsumerService;


@Component
public class RabbitMQNotificationReceiver {

    @Autowired
    private RabbitMQConsumerService rabbitService;

    public void receiveMessage(rabbitMQNotificationDto message) {
        System.out.println("Received message: " + message);
        rabbitService.processNotification(message);

    }

}

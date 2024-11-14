package com.notification.notificationManagement.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.notificationManagement.dtos.requests.rabbitMQNotificationDto;

@Service
public class RabbitMQProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(rabbitMQNotificationDto message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
        System.out.println("Message sent to RabbitMQ: " + message.getContent());
    }

}

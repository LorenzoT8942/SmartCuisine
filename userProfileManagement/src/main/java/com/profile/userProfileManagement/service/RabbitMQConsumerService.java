package com.profile.userProfileManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.profile.userProfileManagement.dtos.requests.rabbitMQNotificationDto;

@Service
public class RabbitMQConsumerService {

    @Value("${external.api.rabbitmq_service.consumer}")
    private String queueUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notServ;

    public void processNotification(rabbitMQNotificationDto notification){
        try{notServ.addNotification(notification);}
        catch(IllegalStateException e){
            System.out.println("Impossible to save the Notification due to "+e.getMessage());
        }
    }

}

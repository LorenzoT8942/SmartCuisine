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



    /*public List<Notification> fetchMessages() {
        ResponseEntity<List<Notification>> response = restTemplate.exchange(
                queueUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Notification>>() {});
        return response.getBody();
    }

    public void processNotifications() {
        List<Notification> notifications = fetchMessages();
        if (notifications != null) {
            notifications.forEach(notification -> {
                // Process each notification here
                System.out.println("Processing notification: " + notification.getContent());
                notServ.addNotification(notification);
            });
        }
    }*/


    public void processNotification(rabbitMQNotificationDto notification){
        notServ.addNotification(notification);
    }

}

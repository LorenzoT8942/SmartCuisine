package com.profile.userProfileManagement.RabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.profile.userProfileManagement.service.RabbitMQConsumerService;

@Service
public class RabbitMQConsumerScheduler {

    @Autowired
    private RabbitMQConsumerService rabbitService;


    @Scheduled(cron = "0 0 * * * *")  // Every hour, on the hour
    public void scheduledTask() {
        System.out.println("Scheduled task triggered, fetching and processing notifications.");
        rabbitService.processNotifications();
    }

}

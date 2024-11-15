package com.notification.notificationManagement.service;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.notificationManagement.RabbitMQ.RabbitMQProducerService;
import com.notification.notificationManagement.dtos.requests.NotificationRequestDto;
import com.notification.notificationManagement.dtos.requests.rabbitMQNotificationDto;
import com.notification.notificationManagement.dtos.responses.NotificationResponseDto;
import com.notification.notificationManagement.model.Notification;
import com.notification.notificationManagement.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notRepo;

    @Autowired
    private RabbitMQProducerService rabbitServ;

    public Optional<NotificationResponseDto> createNotification(NotificationRequestDto dto){
        Notification not = new Notification();
        not.setTimestamp(LocalDateTime.now());
        not.setContent(dto.getContent());
        not.setUsername(dto.getUsername());
        try{ 
            not = notRepo.save(not);
            NotificationResponseDto resDto = new NotificationResponseDto();
            resDto.setContent(not.getContent());
            resDto.setTimestamp(not.getTimestamp());
            resDto.setUsername(not.getUsername());
            sendNotificationToRabbit(not);
            return Optional.of(resDto);
        } catch(IllegalStateException e){
            return Optional.empty();
        }
    }

    private void sendNotificationToRabbit(Notification not){
        System.out.println("sending message: "+not.getContent()+" to RabbitMQ");
        rabbitMQNotificationDto dto = new rabbitMQNotificationDto();
        dto.setContent(not.getContent());
        dto.setNotificationId(not.getId());
        dto.setUserProfileUsername(not.getUsername());
        rabbitServ.sendMessage(dto);
    }

}

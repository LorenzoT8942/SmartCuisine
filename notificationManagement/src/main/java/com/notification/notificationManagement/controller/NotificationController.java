package com.notification.notificationManagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.notificationManagement.dtos.requests.NotificationRequestDto;
import com.notification.notificationManagement.dtos.responses.NotificationResponseDto;
import com.notification.notificationManagement.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notServ;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody NotificationRequestDto notificationDto) {
        Optional<NotificationResponseDto> resDto = notServ.createNotification(notificationDto);
        if(resDto.isPresent()) return new ResponseEntity<>(resDto, HttpStatus.OK);
        else return new ResponseEntity<>("error in creating the notification", HttpStatus.NOT_ACCEPTABLE);
    }
}

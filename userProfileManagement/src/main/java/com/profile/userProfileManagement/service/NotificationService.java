package com.profile.userProfileManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.userProfileManagement.dtos.requests.rabbitMQNotificationDto;
import com.profile.userProfileManagement.model.Notification;
import com.profile.userProfileManagement.model.UserProfile;
import com.profile.userProfileManagement.repository.NotificationRepository;
import com.profile.userProfileManagement.repository.userProfileRepository;

@Service
public class NotificationService {


    @Autowired
    private NotificationRepository notRepo;

    @Autowired
    private userProfileRepository userRepo;


    @Transactional
    public void addNotification(rabbitMQNotificationDto not){
        Optional<UserProfile> user = userRepo.findOneByusername(not.getUserProfileUsername());

        System.out.println(not);

        if(user.isEmpty()) throw new IllegalStateException("username del messaggio non trovato");

        Notification n = new Notification();
        n.setContent(not.getContent());
        n.setNotificationId(not.getNotificationId());
        n.setUserProfile(user.get());

        notRepo.saveAndLinkNotification(n);
    }

}

package com.profile.userProfileManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.userProfileManagement.model.Notification;
import com.profile.userProfileManagement.repository.NotificationRepository;

@Service
public class NotificationService {


    @Autowired
    private NotificationRepository notRepo;

    public void addNotification(Notification not){
        notRepo.save(not);
    }

}

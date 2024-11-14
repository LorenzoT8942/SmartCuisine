package com.notification.notificationManagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notification.notificationManagement.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

}

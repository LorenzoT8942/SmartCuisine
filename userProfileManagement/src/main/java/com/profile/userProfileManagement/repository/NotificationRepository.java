package com.profile.userProfileManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.profile.userProfileManagement.model.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO notifications (notification_id, content, user_profile) 
        VALUES (:#{#notification.notificationId}, :#{#notification.content}, :#{#notification.userProfile.username});
        
        INSERT INTO user_profiles_notifications (user_profile_username, notification_id) 
        VALUES (:#{#notification.userProfile.username}, :#{#notification.notificationId});
        """, nativeQuery = true)
    void saveAndLinkNotification(Notification notification);


}

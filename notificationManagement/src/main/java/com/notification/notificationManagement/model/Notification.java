package com.notification.notificationManagement.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "userProfile", nullable = false)
    private String username;

    @Column(name = "timestamp", nullable = false, updatable = false)
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

}

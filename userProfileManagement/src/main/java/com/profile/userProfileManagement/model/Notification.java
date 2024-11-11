package com.profile.userProfileManagement.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long notificationId;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "userProfile", nullable = false)
    private userProfile userProfile;

}

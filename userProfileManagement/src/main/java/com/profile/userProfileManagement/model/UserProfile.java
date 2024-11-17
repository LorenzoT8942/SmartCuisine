package com.profile.userProfileManagement.model;

import java.util.ArrayList;
import java.util.List;

import com.profile.userProfileManagement.model.enums.genderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "userProfiles")
public class UserProfile {

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "gender")
    private genderEnum gender;

    @Column(name = "hashPassword", nullable = false)
    private String hashPassword;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="user_profiles_notifications",
            joinColumns = @JoinColumn(name="userProfile_username", referencedColumnName="username"),
            inverseJoinColumns=@JoinColumn(name="notification_id", referencedColumnName="notificationId")
    )
    @Column(updatable = false)
    private List<Notification> notifications = new ArrayList<>();


}

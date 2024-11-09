package com.profile.userProfileManagement.model;

import java.util.HashSet;
import java.util.Set;

import com.profile.userProfileManagement.model.enums.genderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class userProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "gender")
    private genderEnum gender;

    @Column(name = "hashPassword", nullable = false)
    private String hashPassword;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="notifications",
            joinColumns = @JoinColumn(name="userProfile_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="notification_id", referencedColumnName="notificationId")
    )
    private Set<Notification> notifications = new HashSet<>();


}

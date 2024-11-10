package com.profile.userProfileManagement.dtos.responses;
import java.util.List;

import com.profile.userProfileManagement.model.Notification;
import com.profile.userProfileManagement.model.enums.genderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userProfileResponseDto {
    private String email;
    private genderEnum gender;
    private String username;
    private List<Notification> notifications;
}

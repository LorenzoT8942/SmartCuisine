package com.profile.userProfileManagement.dtos.requests;

import com.profile.userProfileManagement.model.enums.genderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userProfileRequestDto {
    private String email;
    private genderEnum gender;
    private String password;
    private String username;

}

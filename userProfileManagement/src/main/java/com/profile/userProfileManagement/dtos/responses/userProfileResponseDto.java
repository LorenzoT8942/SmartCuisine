package com.profile.userProfileManagement.dtos.responses;
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
    private String Password;
}

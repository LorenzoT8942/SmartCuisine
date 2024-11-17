package com.profile.userProfileManagement.dtos.requests;

import com.profile.userProfileManagement.model.enums.genderEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userProfileRequestDto {

    @NotNull
    private String email;

    @NotNull
    private genderEnum gender;

    @NotNull
    private String password;
    
    @NotNull
    private String username;

}

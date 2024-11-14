package com.notification.notificationManagement.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class rabbitMQNotificationDto {

    @NotNull
    private Long notificationId;

    @NotNull
    private String content;

    @NotNull
    private String userProfileUsername;

}

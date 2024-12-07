package com.storage.storageManagement.dtos.request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationRequestDto {

    @NotNull
    private String content;
    @NotNull
    private String username;
}

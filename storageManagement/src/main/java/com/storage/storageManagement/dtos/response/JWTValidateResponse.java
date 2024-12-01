package com.storage.storageManagement.dtos.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTValidateResponse {
    private boolean isValid;
    private String message;
}

package com.authentication.authenticationManagement.dtos.requests;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTRequest {
    private String username;
}

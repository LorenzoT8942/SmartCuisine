package com.authentication.authenticationManagement.dtos.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTRequest {
    private String username;
}

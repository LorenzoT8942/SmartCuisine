package com.authentication.authenticationManagement.dtos.responses;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse {
    private String jwtToken;
}

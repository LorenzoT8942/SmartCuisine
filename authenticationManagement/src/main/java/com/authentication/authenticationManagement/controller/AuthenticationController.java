package com.authentication.authenticationManagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.authenticationManagement.dtos.requests.JWTRequest;
import com.authentication.authenticationManagement.dtos.responses.JWTResponse;
import com.authentication.authenticationManagement.dtos.responses.JWTValidateResponse;
import com.authentication.authenticationManagement.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authServ;

    @PostMapping("/token")
    public ResponseEntity<JWTResponse> createToken(@RequestBody JWTRequest request) {
        // Authenticated with the API key
        
        String token = authServ.generateToken(request.getUsername());
        return ResponseEntity.ok(new JWTResponse(token));
    }

    @GetMapping("/validate")
    public ResponseEntity<JWTValidateResponse> validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        JWTValidateResponse resp = new JWTValidateResponse();
        if (authServ.validateToken(token)) {
            String username = authServ.getUsernameFromToken(token);
            resp.setValid(true);
            resp.setMessage(username);
            return ResponseEntity.ok(resp);
        } else {
            resp.setValid(false);
            resp.setMessage("Token non valido o scaduto");
            return ResponseEntity.status(401).body(resp);
        }
    }

}

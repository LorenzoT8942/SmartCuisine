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
import com.authentication.authenticationManagement.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authServ;

    @PostMapping("/token")
    public ResponseEntity<JWTResponse> createToken(@RequestBody JWTRequest request) {
        // Autenticazione del servizio chiamante
        //return ResponseEntity.status(403).body(null); // Forbidden
        
        String token = authServ.generateToken(request.getUsername());
        return ResponseEntity.ok(new JWTResponse(token));
    }

    // Endpoint per validare un token
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        if (authServ.validateToken(token)) {
            String username = authServ.getUsernameFromToken(token);
            return ResponseEntity.ok("Token valido per l'utente: " + username);
        } else {
            return ResponseEntity.status(401).body("Token non valido o scaduto");
        }
    }

}

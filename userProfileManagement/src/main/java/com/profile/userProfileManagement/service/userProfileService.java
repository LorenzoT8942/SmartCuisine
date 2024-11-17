package com.profile.userProfileManagement.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.userProfileManagement.dtos.requests.loginDto;
import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.dtos.requests.userProfileUpdateDto;
import com.profile.userProfileManagement.dtos.responses.JWTResponse;
import com.profile.userProfileManagement.dtos.responses.userProfileResponseDto;
import com.profile.userProfileManagement.model.UserProfile;
import com.profile.userProfileManagement.repository.userProfileRepository;
import com.profile.userProfileManagement.utilities.JWTContext;

@Service
public class userProfileService {

    @Autowired
    private userProfileRepository userPRepo;

    @Value("${external.api.auth_service.token}")
    private String getTokenUrl;

    @Value("${authentication.service.apikey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }



    public userProfileResponseDto createUserProfile(userProfileRequestDto dto) throws Exception{
        UserProfile u = new UserProfile();
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setUsername(dto.getUsername());
        u.setHashPassword(hashPassword(dto.getPassword()));

        if (userPRepo.findOneByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile already created for user " + dto.getUsername());
        }

        if (userPRepo.findOneByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists " + dto.getEmail());
        }
        u = userPRepo.save(u);

        return objectMapper.convertValue(u, userProfileResponseDto.class);
    }

    public ResponseEntity<Object> login(loginDto dto){

        Optional<UserProfile> up = userPRepo.findOneByUsername(dto.getUsername());

        //Optional<userProfile> up = userPRepo.findByUsernameAndHashPassword(dto.getUsername(), hashPassword(dto.getPassword()));
        if (up.isEmpty()) return new ResponseEntity<>("username not present", HttpStatus.NOT_FOUND);
        if(!verifyPassword(dto.getPassword(), up.get().getHashPassword())) return new ResponseEntity<>("the password is wrong", HttpStatus.FORBIDDEN);


        String username = up.get().getUsername();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", apiKey);
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("username", username);

        // Wrap the request payload in an HttpEntity with headers
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Make the POST request
        ResponseEntity<JWTResponse> jwt = restTemplate.exchange(getTokenUrl, HttpMethod.POST, requestEntity, JWTResponse.class);

        if (jwt.getStatusCode().isError() || !jwt.hasBody() || jwt.getBody().getJwtToken() == null) return new ResponseEntity<>("error in generating the jwt token", HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(jwt.getBody(), HttpStatus.OK);
    }


    public userProfileResponseDto getprofile(String username){
        Optional<UserProfile> up = userPRepo.findOneByUsername(username);
        if (up.isEmpty()) return null;
        UserProfile user = up.get();
        return objectMapper.convertValue(user, userProfileResponseDto.class);
    }

    public ResponseEntity<String> deleteprofile(String user){
        if(!user.equals(JWTContext.get())) return new ResponseEntity<>("Username not corresponding wrt the logged one", HttpStatus.FORBIDDEN);
        Optional<UserProfile> us = userPRepo.findOneByUsername(user);
        if(us.isEmpty()) return new ResponseEntity<>("Username not found in the system", HttpStatus.NOT_FOUND);
        userPRepo.delete(us.get());
        return new ResponseEntity<>("Deleted the user "+user+" correctly", HttpStatus.OK);
    }


    public ResponseEntity<Object> updateprofile(String user, userProfileUpdateDto dto){
        if(!user.equals(JWTContext.get())) return new ResponseEntity<>("Username not corresponding wrt the logged one", HttpStatus.FORBIDDEN);
        Optional<UserProfile> us = userPRepo.findOneByUsername(user);
        if(us.isEmpty()) return new ResponseEntity<>("The username object of the request was not found in the system", HttpStatus.NOT_FOUND);
        UserProfile up = us.get();

        if(dto.getEmail()!= null && userPRepo.findOneByEmail(dto.getEmail()).isPresent()) return new ResponseEntity<>("The email already exists", HttpStatus.FORBIDDEN);
        
        if(dto.getEmail() != null) up.setEmail(dto.getEmail());
        if(dto.getGender() != null) up.setGender(dto.getGender());
        if(dto.getPassword() != null) up.setHashPassword(hashPassword(dto.getPassword()));

        up = userPRepo.save(up);
        return new ResponseEntity<>(objectMapper.convertValue(up, userProfileResponseDto.class), HttpStatus.OK);
    }




}

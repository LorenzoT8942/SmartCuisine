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

import com.profile.userProfileManagement.dtos.requests.loginDto;
import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.dtos.responses.JWTResponse;
import com.profile.userProfileManagement.dtos.responses.userProfileResponseDto;
import com.profile.userProfileManagement.model.userProfile;
import com.profile.userProfileManagement.repository.userProfileRepository;

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



    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }



    public userProfile createUserProfile(userProfileRequestDto dto) throws Exception{
        userProfile u = new userProfile();
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setUsername(dto.getUsername());
        u.setHashPassword(hashPassword(dto.getPassword()));

        if (userPRepo.findOneByusername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile already created for user " + dto.getUsername());
        }

        //check if email already exists
        if (userPRepo.findOneByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists " + dto.getEmail());
        }
        return userPRepo.save(u);
    }

    public ResponseEntity<Object> login(loginDto dto){

        Optional<userProfile> up = userPRepo.findOneByusername(dto.getUsername());

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
        Optional<userProfile> up = userPRepo.findOneByusername(username);
        if (up.isEmpty()) return null;
        userProfile user = up.get();

        userProfileResponseDto dto = new userProfileResponseDto();
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setUsername(user.getUsername());
        dto.setNotifications(user.getNotifications());

        return dto;
    }




}

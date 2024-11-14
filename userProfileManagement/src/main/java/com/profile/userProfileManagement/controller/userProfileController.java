package com.profile.userProfileManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.userProfileManagement.dtos.requests.loginDto;
import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.dtos.responses.userProfileResponseDto;
import com.profile.userProfileManagement.service.userProfileService;
import com.profile.userProfileManagement.utilities.JWTContext;


@RestController
@RequestMapping("/profiles")
public class userProfileController {


    @Autowired
    private userProfileService userPServ;


    @PostMapping("/create")
    public ResponseEntity<Object> createProfile(@RequestBody userProfileRequestDto userProfileRequestDto) {
        try {
            userProfileResponseDto createdProfile = userPServ.createUserProfile(userProfileRequestDto);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody loginDto loginDto) {
         return userPServ.login(loginDto);
    }


    @GetMapping("/profile")
    public ResponseEntity<Object> getProfileData() {
        String username = JWTContext.get();
        userProfileResponseDto result = userPServ.getprofile(username);
        if (result == null){
            return new ResponseEntity<>("error in retrieving user data from username " + username, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
    




}

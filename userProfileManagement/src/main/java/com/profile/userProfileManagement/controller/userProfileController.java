package com.profile.userProfileManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.model.userProfile;
import com.profile.userProfileManagement.service.userProfileService;

@RestController
@RequestMapping("/profiles")
public class userProfileController {


    @Autowired
    private userProfileService userPServ;


    @PostMapping("/create")
    public ResponseEntity<userProfile> createProfile(@RequestBody userProfileRequestDto userProfileRequestDto) {
        userProfile createdProfile = userPServ.createUserProfile(userProfileRequestDto);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }



}

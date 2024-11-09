package com.profile.userProfileManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.model.userProfile;
import com.profile.userProfileManagement.repository.userProfileRepository;

@Service
public class userProfileService {

    @Autowired
    private userProfileRepository userPRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }



    public userProfile createUserProfile(userProfileRequestDto dto){
        userProfile u = new userProfile();
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setHashPassword(hashPassword(dto.getPassword()));
        return userPRepo.save(u);
    }


}

package com.profile.userProfileManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.userProfileManagement.dtos.requests.loginDto;
import com.profile.userProfileManagement.dtos.requests.userProfileRequestDto;
import com.profile.userProfileManagement.dtos.requests.userProfileUpdateDto;
import com.profile.userProfileManagement.dtos.responses.FavoriteRecipesResponseDTO;
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
        System.out.println("sono nel controller, helo\n\n");
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


    @DeleteMapping("/profile/{username}")
    public ResponseEntity<String> deleteProfile(@PathVariable String username){
        return userPServ.deleteprofile(username);
    }

    @PutMapping("profile/{username}")
    public ResponseEntity<Object> updateProfile(@PathVariable String username, @RequestBody userProfileUpdateDto entity) {
        return userPServ.updateprofile(username, entity);
    }

    @GetMapping("/profile/favorites")
    public ResponseEntity<FavoriteRecipesResponseDTO> getFavorites() {
        String username = JWTContext.get();
        return userPServ.getFavorites(username);
    }

    @PostMapping("/profile/favorites/{recipeId}")
    public ResponseEntity<FavoriteRecipesResponseDTO> addFavorite(@PathVariable Long recipeId) {
        String username = JWTContext.get();
        return userPServ.addFavorite(username, recipeId);
    }

    @DeleteMapping("/profile/favorites/{recipeId}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long recipeId) {
        String username = JWTContext.get();
        return userPServ.deleteFavorite(username, recipeId);
    }
}

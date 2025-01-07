package com.storage.storageManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storage.storageManagement.dtos.request.AddIngredientRequestDTO;
import com.storage.storageManagement.dtos.response.StorageListResponseDTO;
import com.storage.storageManagement.service.StorageListService;
import com.storage.storageManagement.utilities.JWTContext;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/storage")
public class StorageListController {

    @Autowired
    private StorageListService storageListService;

    /**
     * Get the storage list of a specific user.
     *
     * @param username The username of the user
     * @return StorageListResponseDTO containing user's storage details
     */
    @GetMapping("/{username}")
    public List<StorageListResponseDTO> getStorageByUsername(@PathVariable String username) {
        if (!JWTContext.get().equals(username)) {
            throw new RuntimeException("User is not authorized to access this resource");
        }
        return storageListService.getStorageByUsername(username);
    }

    /**
     * Add an ingredient to the user's storage with quantity and expiration date.
     *
     * @param addIngredientRequest The data of the ingredient to be added
     * @return Updated StorageListResponseDTO
     */
    @PostMapping("/add-ingredient")
    public StorageListResponseDTO addIngredientToStorage(@RequestBody AddIngredientRequestDTO addIngredientRequest) {
        return storageListService.addIngredientToStorage(addIngredientRequest);
    }

    @DeleteMapping("/deleteIngredient/{ingredientId}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long ingredientId){
        return storageListService.deleteIngredientToStorage(ingredientId);

    }


    @PostMapping("/move-ingredients/{shListId}")
    public ResponseEntity<String> moveIngredients(@PathVariable String shListId, HttpServletRequest request) { 
        
        return storageListService.moveIngredientsToStorage(shListId, extractJWTTokenFromRequest(request));       
    }


    private String extractJWTTokenFromRequest(HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token
            String jwtToken = authorizationHeader.substring(7);
            return jwtToken;
        } else {
            return null;
        }
    }
    

}



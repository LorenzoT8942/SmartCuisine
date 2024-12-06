package com.storage.storageManagement.controller;

import com.storage.storageManagement.dtos.request.AddIngredientRequestDTO;
import com.storage.storageManagement.dtos.response.StorageListResponseDTO;
import com.storage.storageManagement.service.StorageListService;
import com.storage.storageManagement.utilities.JWTContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            throw new CustomUnauthorizedException("User is not authorized to access this resource");
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

    public static class CustomUnauthorizedException extends RuntimeException {
        public CustomUnauthorizedException(String message) {
            super(message);
        }
    }
}



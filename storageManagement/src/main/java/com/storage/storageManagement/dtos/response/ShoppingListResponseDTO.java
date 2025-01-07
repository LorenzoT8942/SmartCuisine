package com.storage.storageManagement.dtos.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListResponseDTO {
    private String username;  // Username of the user who owns the shopping list
    private String name;      // Name of the shopping list
    private Map<Long, Float> ingredients; // ingredients of the shopping list
}

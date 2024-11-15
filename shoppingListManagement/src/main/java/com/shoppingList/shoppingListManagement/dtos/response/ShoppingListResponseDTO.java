package com.shoppingList.shoppingListManagement.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListResponseDTO {
    private String username;  // Username of the user who owns the shopping list
    private String name;      // Name of the shopping list
}

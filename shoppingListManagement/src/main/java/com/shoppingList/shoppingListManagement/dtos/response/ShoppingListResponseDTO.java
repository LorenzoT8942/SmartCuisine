package com.shoppingList.shoppingListManagement.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListResponseDTO {
    private String username;    // Username del proprietario della lista
    private String name;        // Nome della lista della spesa
    private List<IngredientResponseDTO> ingredients;  // Lista degli ingredienti della lista
}

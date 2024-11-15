package com.shoppingList.shoppingListManagement.dtos.response;

import com.shoppingList.shoppingListManagement.dtos.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddIngredientResponseDTO {
    private String username;  // Username of the user who owns the shopping list
    private String shoppingListName;  // Name of the shopping list

    // Mappa degli ingredienti con ID e quantità nella lista aggiornata
    private Map<Long, Float> ingredients;  // Ingredienti attuali nella shopping list
}

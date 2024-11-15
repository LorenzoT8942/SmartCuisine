package com.shoppingList.shoppingListManagement.dtos.request;

import com.shoppingList.shoppingListManagement.dtos.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddIngredientRequestDTO {

    private String username;
    private String shoppingListName;

    // Lista di ingredienti con ID e quantità
    private List<IngredientDTO> ingredients;  // Ingredienti attuali nella shopping list
}

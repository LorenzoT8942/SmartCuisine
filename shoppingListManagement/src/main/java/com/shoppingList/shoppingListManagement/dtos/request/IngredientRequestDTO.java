package com.shoppingList.shoppingListManagement.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredientRequestDTO {

    private Long ingredientId;  // ID dell'ingrediente
    private Float quantity;    // Quantità dell'ingrediente (es. "2 kg", "1 liter")
}
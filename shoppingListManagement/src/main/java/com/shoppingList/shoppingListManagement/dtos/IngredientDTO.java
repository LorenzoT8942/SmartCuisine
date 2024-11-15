package com.shoppingList.shoppingListManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredientDTO {

    private Long ingredientId;
    private Float quantity; // Quantità in grammi
}

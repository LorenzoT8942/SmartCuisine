package com.shoppingList.shoppingListManagement.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {
    private Long ingredientId;
    private Float quantity;
}

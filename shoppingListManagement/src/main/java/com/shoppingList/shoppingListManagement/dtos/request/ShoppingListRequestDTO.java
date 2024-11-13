package com.shoppingList.shoppingListManagement.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListRequestDTO {
    private String name;  // Nome della lista della spesa
    private List<IngredientRequestDTO> ingredients;  // Lista degli ingredienti inviati dal client
}

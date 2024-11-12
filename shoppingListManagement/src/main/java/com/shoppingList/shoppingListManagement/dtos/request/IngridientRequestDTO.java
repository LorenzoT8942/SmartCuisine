package com.shoppingList.shoppingListManagement.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDTO {
    private String name;      // Nome dell'ingrediente
    private String quantity;  // Quantità richiesta dall'utente
}

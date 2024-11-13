package com.shoppingList.shoppingListManagement.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {
    private Long id;         // Identificativo unico dell'ingrediente
    private String name;     // Nome dell'ingrediente
    private String quantity; // Quantità disponibile
}

package com.shoppingList.shoppingListManagement.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListRequestDTO {
    private String name;  // Name of the shopping list
}

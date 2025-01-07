package com.shoppingList.shoppingListManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shopping_lists")
public class ShoppingList implements Serializable {

    @EmbeddedId
    private ShoppingListID id;  // Composite key for the table shopping_lists (username and shopping list name)

    @ElementCollection
    @Column(name = "ingredients")
    private Map<Long, Float> ingredients = new HashMap<>();

    public void addIngredient(Long id, Float quantity) {this.ingredients.put(id, quantity);}
}

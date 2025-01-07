package com.shoppingList.shoppingListManagement.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void removeIngredient(Long id) {
        if(this.ingredients.containsKey(id)) this.ingredients.remove(id);
    }
}

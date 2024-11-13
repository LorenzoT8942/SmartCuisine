package com.shoppingList.shoppingListManagement.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shopping_lists")
public class ShoppingList implements Serializable {

    @EmbeddedId
    private ShoppingListID id;  // Composite key for the table shopping_lists (username and name)
}

package com.shoppingList.shoppingListManagement.model;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shopping_lists")
public class ShoppingList implements Serializable {

    @EmbeddedId
    private ShoppingListId id;  // Chiave composta per la tabella shopping_lists

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "shopping_list_ingredients",
            joinColumns = {
                    @JoinColumn(name = "username", referencedColumnName = "username"),
                    @JoinColumn(name = "name", referencedColumnName = "name")
            },
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();
}

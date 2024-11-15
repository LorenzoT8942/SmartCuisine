package com.shoppingList.shoppingListManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ShoppingListID implements Serializable {

    @Column(name = "username")
    private String username;

    @Column(name = "shopping_list_name")
    private String shoppingListName;
}

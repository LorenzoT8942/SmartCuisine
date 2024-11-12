package com.shoppingList.shoppingListManagement.repository;

import com.shoppingList.shoppingListManagement.model.ShoppingList;
import com.shoppingList.shoppingListManagement.model.ShoppingListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, ShoppingListId> {

    // Metodo custom per trovare tutte le liste di un determinato utente
    List<ShoppingList> findByIdUsername(String username);

    // other methods
}

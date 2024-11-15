package com.shoppingList.shoppingListManagement.repository;

import com.shoppingList.shoppingListManagement.model.ShoppingList;
import com.shoppingList.shoppingListManagement.model.ShoppingListID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, ShoppingListID> {

    // Metodo custom per trovare tutte le liste di un determinato utente
    List<ShoppingList> findByIdUsername(String username);


    @Query(value = """
        SELECT * FROM shopping_lists
        WHERE username = :#{#id.username} AND shopping_list_name = :#{#id.shoppingListName}
        """, nativeQuery = true)
    Optional<ShoppingList> findOneByShoppingListID(ShoppingListID id);

    // other methods
}

package com.shoppingList.shoppingListManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingList.shoppingListManagement.dtos.request.AddIngredientRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.request.ShoppingListRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.response.AddIngredientResponseDTO;
import com.shoppingList.shoppingListManagement.dtos.response.ShoppingListResponseDTO;
import com.shoppingList.shoppingListManagement.service.ShoppingListService;
import com.shoppingList.shoppingListManagement.utilities.JWTContext;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    /**
     * Endpoint to retrieve all shopping lists for the authenticated user.
     *
     * @return List of ShoppingListResponseDTO
     */
    @GetMapping
    public List<ShoppingListResponseDTO> getAllShoppingLists() {
        String username = JWTContext.get();
        return shoppingListService.getAllShoppingLists(username);
    }

    /**
     * Endpoint to retrieve a specific shopping list by name for the authenticated user.
     *
     * @param name The name of the shopping list to retrieve
     * @return ShoppingListResponseDTO
     */
    @GetMapping("/{name}")
    public ShoppingListResponseDTO getShoppingListById(@PathVariable String name) {
        String username = JWTContext.get();
        return shoppingListService.getShoppingListById(username, name);
    }

    /**
     * Endpoint to create a new shopping list for the authenticated user.
     *
     * @param shoppingListRequestDTO The data for creating a new shopping list
     * @return ShoppingListResponseDTO
     */
    @PostMapping("/create")
    public ShoppingListResponseDTO createShoppingList(@RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
        String username = JWTContext.get();
        return shoppingListService.createShoppingList(username, shoppingListRequestDTO);
    }

    /**
     * Endpoint to delete a specific shopping list by name for the authenticated user.
     *
     * @param name The name of the shopping list to delete
     */
    @DeleteMapping("/{name}")
    public void deleteShoppingList(@PathVariable String name) {
        String username = JWTContext.get();
        shoppingListService.deleteShoppingList(username, name);
    }

    @PostMapping("/add-ingredient")
    public AddIngredientResponseDTO addIngredientToShoppingList(@RequestBody AddIngredientRequestDTO addIngredientRequestDTO) {
        return shoppingListService.addIngredientToShoppingList(addIngredientRequestDTO);
    }
}

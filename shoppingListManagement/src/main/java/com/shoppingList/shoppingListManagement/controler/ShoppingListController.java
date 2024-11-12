package com.shoppingList.shoppingListManagement.controller;

import com.shoppingList.shoppingListManagement.dtos.request.ShoppingListRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.response.ShoppingListResponseDTO;
import com.shoppingList.shoppingListManagement.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-lists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping
    public List<ShoppingListResponseDTO> getAllShoppingLists(@RequestHeader("Authorization") String jwtToken) {
        String username = extractUsernameFromJwt(jwtToken);
        return shoppingListService.getAllShoppingLists(username);
    }

    @GetMapping("/{name}")
    public ShoppingListResponseDTO getShoppingListById(@RequestHeader("Authorization") String jwtToken, @PathVariable String name) {
        String username = extractUsernameFromJwt(jwtToken);
        return shoppingListService.getShoppingListById(username, name);
    }

    @PostMapping
    public ShoppingListResponseDTO createShoppingList(@RequestHeader("Authorization") String jwtToken, @RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
        String username = extractUsernameFromJwt(jwtToken);
        return shoppingListService.createShoppingList(username, shoppingListRequestDTO);
    }

    @DeleteMapping("/{name}")
    public void deleteShoppingList(@RequestHeader("Authorization") String jwtToken, @PathVariable String name) {
        String username = extractUsernameFromJwt(jwtToken);
        shoppingListService.deleteShoppingList(username, name);
    }

    private String extractUsernameFromJwt(String jwtToken) {
        // Metodo di esempio per estrarre lo username dal JWT Token
        return JwtUtils.getUsernameFromToken(jwtToken);
    }
}

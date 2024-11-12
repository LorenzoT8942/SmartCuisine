package com.shoppingList.shoppingListManagement.service;

import com.shoppingList.shoppingListManagement.dtos.request.ShoppingListRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.response.ShoppingListResponseDTO;
import com.shoppingList.shoppingListManagement.dtos.response.IngredientResponseDTO;
import com.shoppingList.shoppingListManagement.model.ShoppingList;
import com.shoppingList.shoppingListManagement.model.ShoppingListId;
import com.shoppingList.shoppingListManagement.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Base URL for the external ingredient microservice
    private static final String INGREDIENTS_API_URL = "http://ingredient-service/api/ingredients";

    /**
     * Retrieve all shopping lists for a specific user.
     *
     * @param username The username of the user
     * @return List of ShoppingListResponseDTO
     */
    public List<ShoppingListResponseDTO> getAllShoppingLists(String username) {
        List<ShoppingList> shoppingLists = shoppingListRepository.findByIdUsername(username);
        return shoppingLists.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific shopping list by username and list name.
     *
     * @param username The username of the user
     * @param listName The name of the shopping list
     * @return ShoppingListResponseDTO
     */
    public ShoppingListResponseDTO getShoppingListById(String username, String listName) {
        ShoppingListId shoppingListId = new ShoppingListId(username, listName);
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new RuntimeException("Shopping list not found"));
        return convertToResponseDTO(shoppingList);
    }

    /**
     * Create a new shopping list for the user.
     *
     * @param username The username of the user
     * @param shoppingListRequestDTO The data of the shopping list to be created
     * @return ShoppingListResponseDTO
     */
    public ShoppingListResponseDTO createShoppingList(String username, ShoppingListRequestDTO shoppingListRequestDTO) {
        ShoppingListId shoppingListId = new ShoppingListId(username, shoppingListRequestDTO.getName());

        // Create ShoppingList entity and save to the repository
        ShoppingList shoppingList = new ShoppingList(shoppingListId);
        shoppingList.setIngredients(
                shoppingListRequestDTO.getIngredients().stream()
                        .map(ingredientRequest -> fetchIngredientByName(ingredientRequest.getName(), ingredientRequest.getQuantity()))
                        .collect(Collectors.toList())
        );

        ShoppingList savedList = shoppingListRepository.save(shoppingList);
        return convertToResponseDTO(savedList);
    }

    /**
     * Delete a specific shopping list for a user.
     *
     * @param username The username of the user
     * @param listName The name of the shopping list
     */
    public void deleteShoppingList(String username, String listName) {
        ShoppingListId shoppingListId = new ShoppingListId(username, listName);
        shoppingListRepository.deleteById(shoppingListId);
    }

    /**
     * Helper method to convert ShoppingList entity to ShoppingListResponseDTO.
     *
     * @param shoppingList The shopping list entity to convert
     * @return ShoppingListResponseDTO
     */
    private ShoppingListResponseDTO convertToResponseDTO(ShoppingList shoppingList) {
        ShoppingListResponseDTO dto = new ShoppingListResponseDTO();
        dto.setUsername(shoppingList.getId().getUsername());
        dto.setName(shoppingList.getId().getName());
        dto.setIngredients(
                shoppingList.getIngredients().stream().map(ingredient -> {
                    IngredientResponseDTO ingredientDTO = new IngredientResponseDTO();
                    ingredientDTO.setId(ingredient.getId());
                    ingredientDTO.setName(ingredient.getName());
                    ingredientDTO.setQuantity(ingredient.getQuantity());
                    return ingredientDTO;
                }).collect(Collectors.toList())
        );
        return dto;
    }

    /**
     * Fetch an ingredient from the external ingredient microservice.
     *
     * @param ingredientName The name of the ingredient to fetch
     * @param quantity The quantity of the ingredient to be added
     * @return IngredientResponseDTO
     */
    private IngredientResponseDTO fetchIngredientByName(String ingredientName, String quantity) {
        // Using RestTemplate to make a request to the external ingredient microservice
        IngredientResponseDTO ingredient = restTemplate.getForObject(INGREDIENTS_API_URL + "/name/" + ingredientName, IngredientResponseDTO.class);

        if (ingredient == null) {
            throw new RuntimeException("Ingredient not found: " + ingredientName);
        }

        // Add the quantity to the fetched ingredient DTO
        ingredient.setQuantity(quantity);

        return ingredient;
    }
}

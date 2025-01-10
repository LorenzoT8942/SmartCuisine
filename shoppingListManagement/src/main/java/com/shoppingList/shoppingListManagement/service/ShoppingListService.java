package com.shoppingList.shoppingListManagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shoppingList.shoppingListManagement.dtos.IngredientDTO;
import com.shoppingList.shoppingListManagement.dtos.request.AddIngredientRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.request.ShoppingListRequestDTO;
import com.shoppingList.shoppingListManagement.dtos.response.AddIngredientResponseDTO;
import com.shoppingList.shoppingListManagement.dtos.response.ShoppingListResponseDTO;
import com.shoppingList.shoppingListManagement.model.ShoppingList;
import com.shoppingList.shoppingListManagement.model.ShoppingListID;
import com.shoppingList.shoppingListManagement.repository.ShoppingListRepository;
import com.shoppingList.shoppingListManagement.utilities.JWTContext;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

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
        ShoppingListID shoppingListId = new ShoppingListID(username, listName);
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
        ShoppingListID shoppingListId = new ShoppingListID(username, shoppingListRequestDTO.getName());

        // Create ShoppingList entity and save to the repository
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(shoppingListId);
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
        ShoppingListID shoppingListId = new ShoppingListID(username, listName);
        shoppingListRepository.deleteById(shoppingListId);
    }

    /**
     * Add an ingredient to an existing shopping list.
     *
     * @param addIngredientRequestDTO The data of the ingredient to be added
     * @return Updated ShoppingListResponseDTO
     */
    public AddIngredientResponseDTO addIngredientToShoppingList(AddIngredientRequestDTO addIngredientRequestDTO) {
        // Find the shopping list to which save the ingredient
        ShoppingListID shoppingListId = new ShoppingListID(
                JWTContext.get(),
                addIngredientRequestDTO.getShoppingListName()
        );

        ShoppingList shoppingList = shoppingListRepository.findOneByShoppingListID(shoppingListId)
                .orElseThrow(() -> new RuntimeException("Shopping list not found"));

        // Iterate over all ingredients from the request DTO
        for (IngredientDTO ingredientRequest : addIngredientRequestDTO.getIngredients()) {
            Long ingredientId = ingredientRequest.getIngredientId();
            float quantityToAdd = ingredientRequest.getQuantity();


            // Use the addIngredient method of ShoppingList to add or update the ingredient
            if (shoppingList.getIngredients().containsKey(ingredientId)) {
                float existingQuantity = shoppingList.getIngredients().get(ingredientId);
                shoppingList.addIngredient(ingredientId, existingQuantity + quantityToAdd);
            } else {
                shoppingList.addIngredient(ingredientId, quantityToAdd);
            }
        }

        //

        // Save the updated shopping list in the repository
        ShoppingList updatedList = shoppingListRepository.save(shoppingList);
        return convertToAddIngredientResponseDTO(updatedList);
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
        dto.setName(shoppingList.getId().getShoppingListName());
        dto.setIngredients(shoppingList.getIngredients());
        return dto;
    }


    private AddIngredientResponseDTO convertToAddIngredientResponseDTO(ShoppingList shoppingList) {
        AddIngredientResponseDTO responseDTO = new AddIngredientResponseDTO();
        responseDTO.setUsername(shoppingList.getId().getUsername());
        responseDTO.setShoppingListName(shoppingList.getId().getShoppingListName());
        responseDTO.setIngredients(shoppingList.getIngredients());
        return responseDTO;
    }

    public ResponseEntity<Object> deleteIngredientToShoppingList(String shopping_list, Long ingredient_id) {
        String username = JWTContext.get();
        if(username == null) return new ResponseEntity<>("Log in for doing the operation", HttpStatus.FORBIDDEN);
        if(shopping_list == null) return new ResponseEntity<>("Insert a valid shopping list name", HttpStatus.BAD_REQUEST);

        ShoppingListID id = new ShoppingListID();
        id.setShoppingListName(shopping_list);
        id.setUsername(username);

        ShoppingList listObj = shoppingListRepository.getReferenceById(id);
        listObj.removeIngredient(ingredient_id);
        listObj = shoppingListRepository.save(listObj);
    
        return new ResponseEntity<>(this.convertToResponseDTO(listObj), HttpStatus.OK);
    }
}

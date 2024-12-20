package com.food.receipesAndIngredientManagement.controller;


import java.util.List;

import com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientInfo.IngredientInfoDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse.RecipeInfoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse.IngredientDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchRecipesByNameResponse.RecipeResultDTO;
import com.food.receipesAndIngredientManagement.service.IngredientService;
import com.food.receipesAndIngredientManagement.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recipes-ingredients")
@RequiredArgsConstructor
public class RecipesIngredientsController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    // Search for an ingredient by name
    @GetMapping("/ingredients/search-by-name")
    public ResponseEntity<List<IngredientDTO>> searchIngredientByName(@RequestParam String name) {
        List<IngredientDTO> ingredients = ingredientService.searchByName(name);
        return ResponseEntity.ok(ingredients);
    }

    // Find an ingredient by ID
    @GetMapping("/ingredients/search-by-id/{id}")
    public ResponseEntity<IngredientInfoDTO> searchIngredientById(@PathVariable Long id) {
        IngredientInfoDTO ingredient = ingredientService.searchIngredientInfo(id);
        return ResponseEntity.ok(ingredient);
    }

    // Search for a recipe by name
    @GetMapping("/recipes/search-by-name")
    public ResponseEntity<List<RecipeResultDTO>> searchRecipeByName(@RequestParam String name) {
        List<RecipeResultDTO> recipes = recipeService.searchRecipeByName(name);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("recipes/info/{id}")
    public ResponseEntity<RecipeInfoResponseDTO> searchRecipeInfo(@PathVariable Long id) {
        RecipeInfoResponseDTO recipe = recipeService.searchRecipeInfo(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("recipes/favorites/add/{id}/{username}")
    public boolean addRecipeToFavorites(@PathVariable Long id, @PathVariable String username){

        //TODO: implementare aggiunta entry nel db

        return false;
    }
}

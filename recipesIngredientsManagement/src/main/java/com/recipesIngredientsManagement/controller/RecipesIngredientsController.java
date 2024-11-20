package com.recipesIngredientsManagement.controller;


import com.recipesIngredientsManagement.dtos.responses.searchIngredientsByNameResponse.IngredientDTO;
import com.recipesIngredientsManagement.dtos.responses.SearchRecipeRequestDTO;
import com.recipesIngredientsManagement.dtos.responses.searchRecipesByNameResponse.RecipeResultDTO;
import com.recipesIngredientsManagement.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        IngredientDTO ingredient = ingredientService.findById(id);
        return ResponseEntity.ok(ingredient);
    }


    // Search for a recipe by name
    @GetMapping("/recipes/search-by-name")
    public ResponseEntity<List<RecipeResultDTO>> searchRecipeByName(@RequestParam String name) {
        List<RecipeResultDTO> recipes = recipeService.searchRecipeByName(name);
        return ResponseEntity.ok(recipes);
    }

    // Find a recipe by ID
    @GetMapping("/recipes/{id}")
    public ResponseEntity<SearchRecipeRequestDTO> getRecipeById(@PathVariable Long id) {
        SearchRecipeRequestDTO recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }

    /*
    // Search for recipes containing an ingredient
    @GetMapping("/recipes/search-by-ingredient")
    public ResponseEntity<List<SearchRecipeRequestDTO>> searchRecipesByIngredient(@RequestParam String ingredientName) {
        List<SearchRecipeRequestDTO> recipes = recipeService.searchByIngredientName(ingredientName);
        return ResponseEntity.ok(recipes);
    }

     */
}

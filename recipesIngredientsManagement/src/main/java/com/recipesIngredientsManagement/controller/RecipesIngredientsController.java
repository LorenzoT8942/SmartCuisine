package com.recipesIngredientsManagement.controller;


import com.recipesIngredientsManagement.service.*;
import com.recipesIngredientsManagement.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recipes-ingredients")
@RequiredArgsConstructor
public class RecipesIngredientsController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    // Search for an ingredient by name
    @GetMapping("/ingredients/search")
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
    @GetMapping("/recipes/search")
    public ResponseEntity<List<RecipeDTO>> searchRecipeByName(@RequestParam String name) {
        List<RecipeDTO> recipes = recipeService.searchByName(name);
        return ResponseEntity.ok(recipes);
    }

    // Find a recipe by ID
    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }
    // Search for recipes containing an ingredient
    @GetMapping("/recipes/search-by-ingredient")
    public ResponseEntity<List<RecipeDTO>> searchRecipesByIngredient(@RequestParam String ingredientName) {
        List<RecipeDTO> recipes = recipeService.searchByIngredientName(ingredientName);
        return ResponseEntity.ok(recipes);
    }
}

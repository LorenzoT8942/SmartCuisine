package com.recipesIngredientsManagement.service;

import com.recipesIngredientsManagement.dtos.RecipeDTO;
import com.recipesIngredientsManagement.repository.RecipeRepository;
import com.recipesIngredientsManagement.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<RecipeDTO> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(recipe -> new RecipeDTO(recipe.getRecipeId(), recipe.getRecipeName()))
                .collect(Collectors.toList());
    }

    // Find recipe by ID
    public RecipeDTO findById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getDescription()))
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + id));
    }

    public List<RecipeDTO> searchByIngredientName(String ingredientName) {
        return recipeRepository.findByIngredientsNameContainingIgnoreCase(ingredientName)
                .stream()
                .map(recipe -> new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getDescription()))
                .collect(Collectors.toList());
    }
}

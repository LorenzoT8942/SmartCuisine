package com.recipesIngredientsManagement.service;

import com.recipesIngredientsManagement.repository.IngredientRepository;

import com.recipesIngredientsManagement.dtos.IngredientDTO;
import com.recipesIngredientsManagement.repository.IngredientRepository;
import com.recipesIngredientsManagement.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> searchByName(String name) {
        return ingredientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ingredient -> new IngredientDTO(ingredient.getId(), ingredient.getName()))
                .collect(Collectors.toList());
    }

    // Find ingredient by ID
    public IngredientDTO findById(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new IngredientDTO(ingredient.getId(), ingredient.getName()))
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found with ID: " + id));
    }
}

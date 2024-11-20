package com.recipesIngredientsManagement.repository;

import com.recipesIngredientsManagement.model.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCase(String name);

    List<Recipe> findByIngredientsNameContainingIgnoreCase(String ingredientName);

}


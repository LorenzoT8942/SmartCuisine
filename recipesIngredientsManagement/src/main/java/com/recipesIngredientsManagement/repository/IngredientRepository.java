package com.recipesIngredientsManagement.repository;

import com.recipesIngredientsManagement.model.Ingredient;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    //Find ingredients whose name contains the name parameter
    List<Ingredient> findByNameContainingIgnoreCase(String name);
}

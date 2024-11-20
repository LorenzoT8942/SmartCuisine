package com.food.receipesAndIngredientManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.receipesAndIngredientManagement.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    //Find ingredients whose name contains the name parameter
    List<Ingredient> findByIngredientNameContainingIgnoreCase(String name);
}

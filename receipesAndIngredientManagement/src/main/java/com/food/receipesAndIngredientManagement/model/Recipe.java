package com.food.receipesAndIngredientManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "recipes")
public class Recipe {

    @Id
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "recipeName")
    private String recipeName;

    @Column(name = "carbohydrates")
    private int carbohydrates;

    @Column(name = "fats")
    private int fats;

    @Column(name = "proteins")
    private int proteins;

    @Column(name = "steps")
    private String steps;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_ingredient", // Join table name
            joinColumns = @JoinColumn(name = "recipe_id"), // Foreign key for Recipe
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Foreign key for Ingredient
    )
    private Set<Ingredient> ingredients = new HashSet<>();
}


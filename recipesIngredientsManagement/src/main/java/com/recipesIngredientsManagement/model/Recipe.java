package com.recipesIngredientsManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "recipes")
public class Recipe {

    @Id
    @Column(name = "recipeId")
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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

}


package com.recipesIngredientManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
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


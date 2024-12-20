package com.food.receipesAndIngredientManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;

    @Column(name = "recipeName")
    private String ingredientName;

    @ManyToMany(mappedBy = "ingredients", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Recipe> recipes = new HashSet<>();

}
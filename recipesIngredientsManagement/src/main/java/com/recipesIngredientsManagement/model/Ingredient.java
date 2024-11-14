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
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @Column(name = "IngredientId")
    private Long ingredientId;

    @Column(name = "recipeName")
    private String ingrdientName;

}
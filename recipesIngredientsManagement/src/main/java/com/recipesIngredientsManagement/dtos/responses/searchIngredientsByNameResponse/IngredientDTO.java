package com.recipesIngredientsManagement.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class IngredientDTO {
    private Long id;
    private String name;
    private String image;
}


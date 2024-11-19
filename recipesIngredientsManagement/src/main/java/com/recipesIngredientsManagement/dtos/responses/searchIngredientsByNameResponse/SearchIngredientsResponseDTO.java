package com.recipesIngredientsManagement.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class SearchIngredientsResponseDTO {

    // Getters and Setters
    private List<IngredientDTO> results;
    private int offset;
    private int number;
    private int totalResults;
}

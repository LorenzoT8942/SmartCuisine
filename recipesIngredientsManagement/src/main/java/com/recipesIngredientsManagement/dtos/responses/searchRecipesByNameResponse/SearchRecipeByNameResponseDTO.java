package com.recipesIngredientsManagement.dtos.responses.searchRecipesByNameResponse;

import java.util.List;

public class SearchRecipeByNameResponseDTO {

    private int offset;
    private int number;
    private List<RecipeResultDTO> results;
    private int totalResults;

    // Getters and Setters
}

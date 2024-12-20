package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipesByNameResponse;

import lombok.Data;

import java.util.List;

@Data
public class SearchRecipeByNameResponseDTO {

    private int offset;
    private int number;
    private List<RecipeResultDTO> results;
    private int totalResults;

    // Getters and Setters
}

package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipesByNameResponse;

import lombok.Data;

@Data
public class RecipeResultDTO {

    private int id;
    private String title;
    private String image;
    private String imageType;

    // Getters and Setters
}

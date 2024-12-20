package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

@Data
public class IngredientDTO {
    private int id;
    private String name;
    private String localizedName;
    private String image;

    // Getters and Setters
}

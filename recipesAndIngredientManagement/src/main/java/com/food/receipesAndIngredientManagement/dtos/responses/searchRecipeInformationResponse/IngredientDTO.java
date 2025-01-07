package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDTO {
    private int id;
    private String name;
    private String localizedName;
    private String image;

    // Getters and Setters
}

package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionalInfoDTO {
    private String calories;
    private String fat;
    private String carbs;
    private String protein;
    // Getters and Setters
}

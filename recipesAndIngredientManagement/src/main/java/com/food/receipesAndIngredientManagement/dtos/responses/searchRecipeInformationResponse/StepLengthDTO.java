package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepLengthDTO {

    private int number;
    private String unit;

    // Getters and Setters

}

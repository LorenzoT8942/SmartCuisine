package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitDTO {

    private double amount;
    private String unitShort;
    private String unitLong;

    // Getters and Setters
}
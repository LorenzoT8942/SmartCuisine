package com.recipesIngredientsManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

@Data
public class UnitDTO {

    private double amount;
    private String unitShort;
    private String unitLong;

    // Getters and Setters
}
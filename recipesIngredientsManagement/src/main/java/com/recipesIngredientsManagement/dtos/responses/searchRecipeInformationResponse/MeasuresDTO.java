package com.recipesIngredientsManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

@Data
public class MeasuresDTO {

    private UnitDTO us;
    private UnitDTO metric;

    // Getters and Setters
}

package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasuresDTO {

    private UnitDTO us;
    private UnitDTO metric;

    // Getters and Setters
}

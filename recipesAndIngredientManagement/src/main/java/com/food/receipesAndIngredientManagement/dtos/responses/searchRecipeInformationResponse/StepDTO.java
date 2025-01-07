package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepDTO {
    private int number;
    private String step;
    private List<IngredientDTO> ingredients;
    private List<IngredientDTO> equipment;
    private StepLengthDTO length;
}


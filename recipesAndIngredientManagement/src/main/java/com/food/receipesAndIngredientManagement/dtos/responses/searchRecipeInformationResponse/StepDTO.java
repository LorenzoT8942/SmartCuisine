package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

import java.util.List;

@Data
public class StepDTO {
    private int number;
    private String step;
    private List<IngredientDTO> ingredients;
    private List<IngredientDTO> equipment;
    private StepLengthDTO length;
}


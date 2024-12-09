package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;

import java.util.List;

@Data
public class InstructionDTO {
    private String name;
    private List<StepDTO> steps;

    // Getters and Setters
}
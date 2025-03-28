package com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIngredientsResponseDTO {

    // Getters and Setters
    private List<IngredientDTO> results;
    private int offset;
    private int number;
    private int totalResults;
}

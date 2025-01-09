package com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDTO {
    private Long id;
    private String name;
    private String image;
}


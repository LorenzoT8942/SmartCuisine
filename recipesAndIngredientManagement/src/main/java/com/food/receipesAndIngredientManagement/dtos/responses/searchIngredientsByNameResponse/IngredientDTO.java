package com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientDTO {
    private Long id;
    private String name;
    private String image;
}


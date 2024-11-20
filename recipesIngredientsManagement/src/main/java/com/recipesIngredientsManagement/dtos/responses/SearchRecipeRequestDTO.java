package com.recipesIngredientsManagement.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRecipeRequestDTO {
    private Long id;
    private String name;
}
package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;
import java.util.List;

@Data
public class ExtendedIngredientDTO {
    private int id;
    private String aisle;
    private String image;
    private String consistency;
    private String name;
    private String nameClean;
    private String original;
    private String originalName;
    private double amount;
    private String unit;
    private List<String> meta;
    private MeasuresDTO measures;

    // Getters and Setters
}

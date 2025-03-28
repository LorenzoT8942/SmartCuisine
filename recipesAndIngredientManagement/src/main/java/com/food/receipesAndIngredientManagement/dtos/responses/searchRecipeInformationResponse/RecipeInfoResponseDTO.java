package com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeInfoResponseDTO {
    private NutritionalInfoDTO nutrition;
    /* 
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private boolean lowFodmap;
    private int weightWatcherSmartPoints;
    private String gaps;
    */
    private int preparationMinutes;
    private int cookingMinutes;
    
    // private int aggregateLikes;
    // private int healthScore;
    // private String creditsText;
    // private String sourceName;
    // private double pricePerServing;
    private List<ExtendedIngredientDTO> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private String imageType;
    private String summary;
    // private List<String> cuisines;
    // private List<String> dishTypes;
    // private List<String> diets;
    // private List<String> occasions;
    private String instructions;
    private List<InstructionDTO> analyzedInstructions;
    // private Object originalId;
    // private double spoonacularScore;
    // private String spoonacularSourceUrl;

    // Getters and Setters
}

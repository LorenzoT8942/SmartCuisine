package com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientInfo;

import lombok.Data;
import java.util.List;

@Data
public class IngredientInfoDTO {

    private int id;
    private String original;
    private String originalName;
    private String name;
    private List<String> possibleUnits;
    private String consistency;
    private List<String> shoppingListUnits;
    private String aisle;
    private String image;
    private List<String> meta;
    private List<String> categoryPath;
}

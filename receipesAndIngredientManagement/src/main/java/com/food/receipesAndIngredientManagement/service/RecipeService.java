package com.food.receipesAndIngredientManagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.receipesAndIngredientManagement.dtos.responses.SearchRecipeRequestDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchRecipeInformationResponse.RecipeInfoResponseDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchRecipesByNameResponse.RecipeResultDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchRecipesByNameResponse.SearchRecipeByNameResponseDTO;
import com.food.receipesAndIngredientManagement.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final String API_KEY = "1b99c9b44ecf4f5080e43718a1c6db89";

    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeResultDTO> searchRecipeByName(String name) {

        List<RecipeResultDTO> recipes = new ArrayList<>();
        // Specify the API URL
        String url = "https://api.spoonacular.com/recipes/complexSearch?query="+ name.strip() + "&addRecipeInformation=false&addRecipeInstructions=true&addRecipeNutrition=false&apiKey=" + API_KEY;
        System.out.println(url);

        // Create an HttpClient
        try (HttpClient client = HttpClient.newHttpClient()) {

            // Build the HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + API_KEY)
                    .GET()
                    .build();

            try {
                // Send the request and get the response
                HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper objectMapper = new ObjectMapper();

                // Parse JSON into IngredientResponseDTO
                SearchRecipeByNameResponseDTO responseDTO = objectMapper.readValue(jsonResponse.body(), SearchRecipeByNameResponseDTO.class);

                // Access individual ingredients
                recipes = responseDTO.getResults();
                recipes.forEach(recipe ->
                        System.out.println(recipe.getTitle())
                );
            } catch (Exception e) {
                // Handle exceptions
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        return recipes;
    }

    public RecipeInfoResponseDTO searchRecipeInfo(Long id) {
        RecipeInfoResponseDTO recipeInfo = new RecipeInfoResponseDTO();

        String url = "https://api.spoonacular.com/recipes/"+ id +"/information?includeNutrition=false&includeWinePairing=false&addTasteData=false&apiKey=" + API_KEY;
        System.out.println(url);

        // Create an HttpClient
        try (HttpClient client = HttpClient.newHttpClient()) {

            // Build the HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + API_KEY)
                    .GET()
                    .build();

            try {
                // Send the request and get the response
                HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(jsonResponse.toString());
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse JSON into IngredientResponseDTO
                recipeInfo = objectMapper.readValue(jsonResponse.body(), RecipeInfoResponseDTO.class);
                recipeInfo.getExtendedIngredients().forEach(ingredient ->
                        System.out.println(ingredient.getName()));

            } catch (Exception e) {
                // Handle exceptions
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        return recipeInfo;
    }
}

package com.recipesIngredientsManagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipesIngredientsManagement.dtos.responses.SearchRecipeRequestDTO;
import com.recipesIngredientsManagement.dtos.responses.searchRecipesByNameResponse.RecipeResultDTO;
import com.recipesIngredientsManagement.dtos.responses.searchRecipesByNameResponse.SearchRecipeByNameResponseDTO;
import com.recipesIngredientsManagement.repository.RecipeRepository;
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
        /*
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(recipe -> new SearchRecipeRequestDTO(recipe.getRecipeId(), recipe.getRecipeName()))
                .collect(Collectors.toList());
         */
        List<RecipeResultDTO> recipes = new ArrayList<>();
        // Specify the API URL
        String url = "https://api.spoonacular.com/recipes/complexSearch?query="+ name.strip() + "&addRecipeInformation=false&addRecipeInstructions=true&addRecipeNutrition=false";

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
                String jsonResponseToStr = jsonResponse.body();

                ObjectMapper objectMapper = new ObjectMapper();

                // Parse JSON into IngredientResponseDTO
                SearchRecipeByNameResponseDTO responseDTO = objectMapper.readValue(jsonResponseToStr, SearchRecipeByNameResponseDTO.class);

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

    // Find recipe by ID
    public SearchRecipeRequestDTO findById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> new SearchRecipeRequestDTO(recipe.getRecipeId(), recipe.getRecipeName()))
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + id));
    }
}

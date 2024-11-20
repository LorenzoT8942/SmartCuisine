package com.recipesIngredientsManagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipesIngredientsManagement.dtos.responses.searchIngredientsByNameResponse.SearchIngredientsResponseDTO;
import com.recipesIngredientsManagement.repository.IngredientRepository;

import com.recipesIngredientsManagement.dtos.responses.searchIngredientsByNameResponse.IngredientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final String API_KEY = "1b99c9b44ecf4f5080e43718a1c6db89";

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> searchByName(String name) {
        return ingredientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ingredient -> new IngredientDTO(ingredient.getIngredientId(), ingredient.getIngredientName(), null))
                .collect(Collectors.toList());
    }

    // Find ingredient by ID
    public IngredientDTO findById(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new IngredientDTO(ingredient.getIngredientId(), ingredient.getIngredientName(), null))
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found with ID: " + id));
    }

    public List<IngredientDTO> searchIngredientsByName(String name){
        List<IngredientDTO> ingredients = new ArrayList<>();
        // Specify the API URL
        String url = "https://api.spoonacular.com/ingredients/complexSearch?query=" + name.strip();

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
                SearchIngredientsResponseDTO responseDTO = objectMapper.readValue(jsonResponseToStr, SearchIngredientsResponseDTO.class);

                // Access individual ingredients
                ingredients = responseDTO.getResults();
                ingredients.forEach(ingredient ->
                        System.out.println(ingredient.getId() + ": " + ingredient.getName() + " (" + ingredient.getImage() + ")")
                );
            } catch (Exception e) {
                // Handle exceptions
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        return ingredients;
    }
}

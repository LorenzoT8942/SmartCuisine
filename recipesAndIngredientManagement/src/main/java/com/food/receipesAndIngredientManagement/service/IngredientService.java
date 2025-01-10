package com.food.receipesAndIngredientManagement.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientInfo.IngredientInfoDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse.IngredientDTO;
import com.food.receipesAndIngredientManagement.dtos.responses.searchIngredientsByNameResponse.SearchIngredientsResponseDTO;
import com.food.receipesAndIngredientManagement.repository.IngredientRepository;

@Service
public class IngredientService {

    private final String API_KEY = "4e5cc22df52c475887ce454cec6659ca";

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> searchByName(String name) {
        List<IngredientDTO> ingredients = new ArrayList<>();
        // Specify the API URL
        String url = "https://api.spoonacular.com/food/ingredients/search?query=" + name.strip() + "&apiKey=" + API_KEY;

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
                System.out.println(jsonResponse.body());
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse JSON into IngredientResponseDTO
                SearchIngredientsResponseDTO responseDTO = objectMapper.readValue(jsonResponse.body(), SearchIngredientsResponseDTO.class);

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

    // Find ingredient by ID
    public IngredientInfoDTO searchIngredientInfo(Long id) {

        IngredientInfoDTO ingredientInfo = new IngredientInfoDTO();
        // Specify the API URL
        String url = "https://api.spoonacular.com/food/ingredients/" + id.toString() + "/information?apiKey=" + API_KEY;

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
                ingredientInfo = objectMapper.readValue(jsonResponse.body(), IngredientInfoDTO.class);

            } catch (Exception e) {
                // Handle exceptions
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        return ingredientInfo;
    }


}

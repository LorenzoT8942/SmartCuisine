package com.storage.storageManagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.storage.storageManagement.dtos.request.AddIngredientRequestDTO;
import com.storage.storageManagement.dtos.response.ShoppingListResponseDTO;
import com.storage.storageManagement.dtos.response.StorageListResponseDTO;
import com.storage.storageManagement.model.StorageList;
import com.storage.storageManagement.model.StorageListID;
import com.storage.storageManagement.repository.StorageListRepository;
import com.storage.storageManagement.utilities.JWTContext;

@Service
public class StorageListService {

    @Autowired
    private StorageListRepository storageListRepository;

    @Value("${external.api.shoppingList_service}")
    private String shoppingListUrl;

    @Autowired
    private RestTemplate restTemplate;

    // Formatter per la data di scadenza nel formato MM/DD/YYYY
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Recupera tutte le entry di storage per un utente specifico.
     *
     * @param username Nome utente dell'utente
     * @return Lista di StorageListResponseDTO contenente i dettagli di tutte le entry di storage dell'utente
     */
    public List<StorageListResponseDTO> getStorageByUsername(String username) {
        // Cerca tutti gli StorageList per l'utente specificato
        List<StorageList> storageListItems = storageListRepository.findByIdUsername(username);

        // Se la lista è vuota, restituisci una lista vuota senza creare nuove entry
        if (storageListItems.isEmpty()) {
            return Collections.emptyList();
        }

        // Converte tutte le entry di storage recuperate in una lista di StorageListResponseDTO
        List<StorageListResponseDTO> responseDTOList = storageListItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // Restituisce la lista di DTO contenenti i dettagli di tutte le entry
        return responseDTOList;
    }


    /**
     * Aggiunge o aggiorna un ingrediente nello storage dell'utente. Se l'ingrediente esiste, lo sovrascrive.
     *
     * @param request Dati dell'ingrediente da aggiungere insieme alla quantità e data di scadenza
     * @return StorageListResponseDTO aggiornato
     */
    public StorageListResponseDTO addIngredientToStorage(AddIngredientRequestDTO request) {
        // Validazione del formato della data di scadenza
        String username = JWTContext.get();
        request.setUsername(username);
        LocalDate expiryDate;
        try {
            expiryDate = request.getExpirationDate();
            if (expiryDate == null) {
                throw new RuntimeException("Expiration date cannot be null. Please provide a valid date in MM/DD/YYYY format.");
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format. Please use MM/DD/YYYY.");
        }

        // Crea o trova la lista di storage per l'utente e l'ingrediente
        StorageListID storageListID = new StorageListID(request.getUsername(), request.getIngredientId());
        Optional<StorageList> optionalStorageList = storageListRepository.findById(storageListID);

        StorageList storageList;
        if (optionalStorageList.isPresent()) {
            // Se l'ingrediente esiste già nello storage, aggiorna la quantità e la data di scadenza
            storageList = optionalStorageList.get();
            storageList.setQuantity(request.getQuantity());
            storageList.setExpiryDate(expiryDate);
        } else {
            // Altrimenti crea un nuovo ingrediente nello storage
            storageList = new StorageList(storageListID, request.getQuantity(), expiryDate);
        }

        // Salva la lista di storage aggiornata nel repository
        StorageList updatedList = storageListRepository.save(storageList);

        // Converti e restituisci il DTO aggiornato
        return convertToResponseDTO(updatedList);
    }


    // Helper method per convertire StorageList in StorageListResponseDTO
    private StorageListResponseDTO convertToResponseDTO(StorageList storageList) {
        StorageListResponseDTO responseDTO = new StorageListResponseDTO();
        responseDTO.setUsername(storageList.getId().getUsername());
        responseDTO.setIngredientId(storageList.getId().getIngredientId());
        responseDTO.setQuantity(storageList.getQuantity());
        responseDTO.setExpirationDate(storageList.getExpiryDate());
        return responseDTO;
    }


    public ResponseEntity<String> deleteIngredientToStorage(Long ingredientId) {
        String username = JWTContext.get();
        if(username == null) return new ResponseEntity<>("Log in for doing the operation", HttpStatus.FORBIDDEN);
        StorageListID id = new StorageListID(username, ingredientId);
        storageListRepository.deleteById(id);
        return new ResponseEntity<>("Ingredient deleted succefully", HttpStatus.OK);
    }

    public ResponseEntity<String> moveIngredientsToStorage(String shListId, String token) {
        if(token == null) return ResponseEntity.badRequest().body("JWT Token is missing or invalid");
        String username = JWTContext.get();
        if(username == null) return new ResponseEntity<>("Log in for doing the operation", HttpStatus.FORBIDDEN);
        //shoppingListUrl
         HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        // Make the GET request
        ResponseEntity<ShoppingListResponseDTO> shListIngrs = restTemplate.exchange(shoppingListUrl + "/shopping-lists/"+shListId, HttpMethod.GET, requestEntity, ShoppingListResponseDTO.class);

        if (shListIngrs.getStatusCode().isError() || !shListIngrs.hasBody()) return new ResponseEntity<>("error in generating retrieving shopping list ingredients", HttpStatus.UNAUTHORIZED);
        
        Map<Long, Float> ings = shListIngrs.getBody().getIngredients();
        AddIngredientRequestDTO dto = new AddIngredientRequestDTO();

        dto.setExpirationDate(LocalDate.now().plusMonths(1));
        dto.setUsername(username);

        for (Map.Entry<Long, Float> ing : ings.entrySet()) {
            Long ingId = ing.getKey();   // Access the key (ingredient ID)
            Float quantity = ing.getValue();
            dto.setIngredientId(ingId);
            dto.setQuantity(quantity);
            this.addIngredientToStorage(dto);
        }

        return new ResponseEntity<>("Ingredients moved succesfully", HttpStatus.OK);
    }
}

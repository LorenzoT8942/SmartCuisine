package com.storage.storageManagement.service;

import com.storage.storageManagement.dtos.request.AddIngredientRequestDTO;
import com.storage.storageManagement.dtos.response.StorageListResponseDTO;
import com.storage.storageManagement.model.StorageList;
import com.storage.storageManagement.model.StorageListID;
import com.storage.storageManagement.repository.StorageListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StorageListService {

    @Autowired
    private StorageListRepository storageListRepository;

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
}

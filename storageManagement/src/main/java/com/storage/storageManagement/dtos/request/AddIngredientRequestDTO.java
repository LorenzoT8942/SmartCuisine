package com.storage.storageManagement.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddIngredientRequestDTO {
    private String username;      // Username dell'utente che possiede lo storage
    private Long ingredientId;    // ID dell'ingrediente
    private Float quantity;       // Quantità dell'ingrediente
    private LocalDate expirationDate; // Data di scadenza dell'ingrediente
}

package com.storage.storageManagement.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageListResponseDTO {

    private String username;        // Nome utente dell'utente che possiede la lista di storage
    private Long ingredientId;      // ID dell'ingrediente presente nello storage
    private Float quantity;         // Quantità dell'ingrediente
    private LocalDate expirationDate;   // Data di scadenza dell'ingrediente (nel formato MM/DD/YYYY)

}

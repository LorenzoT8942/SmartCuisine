package com.storage.storageManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "storage_lists")
public class StorageList implements Serializable {

    @EmbeddedId
    private StorageListID id;  // Chiave composta (username, ingredient_id)

    @Column(name = "quantity", nullable = false)
    private Float quantity;  // Quantità dell'ingrediente

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;  // Data di scadenza dell'ingrediente

    public void addOrUpdateIngredient(String username, Long ingredientId, Float quantity, LocalDate expiryDate) {
        // Crea o aggiorna la chiave primaria composta
        if (this.id == null) {
            this.id = new StorageListID(username, ingredientId);
        } else {
            this.id.setUsername(username);
            this.id.setIngredientId(ingredientId);
        }

        // Aggiorna la quantità e la data di scadenza dell'ingrediente
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
}

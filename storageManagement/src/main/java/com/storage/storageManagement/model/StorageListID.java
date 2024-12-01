package com.storage.storageManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class StorageListID implements Serializable {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;
}

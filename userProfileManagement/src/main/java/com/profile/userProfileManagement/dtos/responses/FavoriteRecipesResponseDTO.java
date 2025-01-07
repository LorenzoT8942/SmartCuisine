package com.profile.userProfileManagement.dtos.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRecipesResponseDTO {
    
    private List<Long> recipeIds;
}

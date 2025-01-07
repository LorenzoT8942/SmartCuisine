package com.profile.userProfileManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.profile.userProfileManagement.model.FavoriteRecipe;
import com.profile.userProfileManagement.model.UserProfile;


@Repository
public interface FavoriteRecipesRepository extends JpaRepository<FavoriteRecipe, Long>{

    List<Long> findAllByUserProfile(UserProfile userProfile);
    void deleteByUserProfileAndRecipeId(UserProfile userProfile, Long recipeId);
}
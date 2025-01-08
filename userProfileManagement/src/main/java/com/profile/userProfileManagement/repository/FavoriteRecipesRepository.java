package com.profile.userProfileManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.profile.userProfileManagement.model.FavoriteRecipe;
import com.profile.userProfileManagement.model.UserProfile;


@Repository
public interface FavoriteRecipesRepository extends JpaRepository<FavoriteRecipe, Long>{

    List<Long> findAllByUserProfile(UserProfile userProfile);

    @Modifying
    @Transactional
    @Query("DELETE FROM FavoriteRecipe fr WHERE fr.recipeId = :recipeId AND fr.userProfile.username = :username")
    void deleteByRecipeIdAndUsername(@Param("recipeId") Long recipeId, @Param("username") String username);

    @Query("SELECT fr.recipeId FROM FavoriteRecipe fr WHERE fr.userProfile = :userProfile")
    List<Long> findAllReceipesByUserProfile(@Param("userProfile") UserProfile userProfile);

}
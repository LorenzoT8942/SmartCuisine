package com.profile.userProfileManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profile.userProfileManagement.model.UserProfile;



@Repository
public interface userProfileRepository extends JpaRepository<UserProfile, String> {

    Optional<UserProfile> findByUsernameAndHashPassword(String username, String hash_pass);

    Optional<UserProfile> findOneByUsername(String username);
    Optional<UserProfile> findOneByEmail(String email);
    Optional<UserProfile> findOneByUsernameOrEmail(String user, String email);


}

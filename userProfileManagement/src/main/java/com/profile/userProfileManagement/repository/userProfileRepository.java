package com.profile.userProfileManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profile.userProfileManagement.model.userProfile;


@Repository
public interface userProfileRepository extends JpaRepository<userProfile, Long> {

    Optional<userProfile> findOneByusernameAndhashPassword(String username, String hash_pass);

    Optional<userProfile> findOneByUsername(String username);

}

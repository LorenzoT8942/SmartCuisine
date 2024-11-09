package com.profile.userProfileManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profile.userProfileManagement.model.userProfile;

@Repository
public interface userProfileRepository extends JpaRepository<userProfile, Long> {


}

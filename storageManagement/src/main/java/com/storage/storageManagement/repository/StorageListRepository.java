package com.storage.storageManagement.repository;

import com.storage.storageManagement.model.StorageList;
import com.storage.storageManagement.model.StorageListID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageListRepository extends JpaRepository<StorageList, StorageListID> {

    /**
     * Find the StorageList by username.
     *
     * @param username The username of the user who owns the storage list
     * @return StorageList entity
     */
    List<StorageList> findByIdUsername(String username);
}

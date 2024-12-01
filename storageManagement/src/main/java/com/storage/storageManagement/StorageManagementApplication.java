package com.storage.storageManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StorageManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageManagementApplication.class, args);
	}

}

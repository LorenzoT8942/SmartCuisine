package com.profile.userProfileManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserProfileManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProfileManagementApplication.class, args);
	}

}

package com.authentication.authenticationManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AuthenticationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationManagementApplication.class, args);
	}

}

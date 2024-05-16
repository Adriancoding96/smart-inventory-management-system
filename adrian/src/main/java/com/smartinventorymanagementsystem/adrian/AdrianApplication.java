package com.smartinventorymanagementsystem.adrian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AdrianApplication {

	//TODO service layer and controller for order related entities
	public static void main(String[] args) {
		SpringApplication.run(AdrianApplication.class, args);
	}

}

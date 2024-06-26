package com.patienthub.patienthubapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PatientHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientHubApplication.class, args);
	}

}

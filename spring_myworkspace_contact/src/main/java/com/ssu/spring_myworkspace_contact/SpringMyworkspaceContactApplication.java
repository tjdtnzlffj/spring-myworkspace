package com.ssu.spring_myworkspace_contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class SpringMyworkspaceContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyworkspaceContactApplication.class, args);
	}

}

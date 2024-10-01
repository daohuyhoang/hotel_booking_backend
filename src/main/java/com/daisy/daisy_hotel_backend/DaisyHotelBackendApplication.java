package com.daisy.daisy_hotel_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DaisyHotelBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaisyHotelBackendApplication.class, args);
	}

}

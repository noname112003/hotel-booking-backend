package com.hotel.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.hotel.common.entity"})
public class HotelBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBackendApplication.class, args);
	}

}

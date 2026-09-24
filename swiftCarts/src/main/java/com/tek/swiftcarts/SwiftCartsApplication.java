package com.tek.swiftcarts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwiftCartsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftCartsApplication.class, args);
	}

}

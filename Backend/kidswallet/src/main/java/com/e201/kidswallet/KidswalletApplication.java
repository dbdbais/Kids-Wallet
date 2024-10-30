package com.e201.kidswallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KidswalletApplication {
	public static void main(String[] args) {
		SpringApplication.run(KidswalletApplication.class, args);
	}

}

package com.hospital.managment.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableMethodSecurity
public class HospitalUserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalUserserviceApplication.class, args);
	}

}

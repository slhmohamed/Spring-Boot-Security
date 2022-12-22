package com.secutity.Security;

import com.secutity.Security.models.Role;
import com.secutity.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class SecurityApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}

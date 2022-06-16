package com.springsecurity.jwtauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class JwtauthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtauthenticationApplication.class, args);
	}

}

package com.epam.authservice.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.authservice.entities.AuthRequest;
import com.epam.authservice.entities.AuthResponse;
import com.epam.authservice.service.AuthService;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	private final AuthService authService;

	
    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
    	//validate credential
		String token=authService.validateCredentialAndGenerateToken(authRequest);

		return new ResponseEntity(new AuthResponse(token, ""), null, HttpStatus.SC_OK);
	}

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }
}

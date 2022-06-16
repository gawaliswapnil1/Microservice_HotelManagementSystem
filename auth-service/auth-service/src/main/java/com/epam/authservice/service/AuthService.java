package com.epam.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.epam.authservice.client.GuestServiceClient;
import com.epam.authservice.entities.AuthRequest;
import com.epam.authservice.entities.AuthResponse;
import com.epam.authservice.entities.Guest;

import lombok.extern.java.Log;

@Service
public class AuthService {

	@Autowired
    private final JwtUtil jwt;

    @Autowired
	private GuestServiceClient guestClient;
    
    @Autowired
    public AuthService(GuestServiceClient guestClient,
                       final JwtUtil jwt) {
        this.guestClient = guestClient;
        this.jwt = jwt;
    }
    
    public String validateCredentialAndGenerateToken(AuthRequest userInput) {
    	//validate credentials ang generatetoken
    	try {
    		Guest guest=Guest.builder().firstName(userInput.getUsername())
    				.password(userInput.getPassword()).build();
    		Optional<Guest> guestFound = guestClient.findGuestByUserNameAndPassword(guest);
    		if(guestFound.isPresent())
    			return jwt.generate(userInput,"");
    	}
    	catch(Exception ex) {
    		
    	}
    	return "Guest not found";
        
    }
    public AuthResponse register(AuthRequest userInput1) {
        //do validation if user already exists
        

		/*
		 * AuthRequest authRequest =
		 * restTemplate.postForObject("http://GUEST-SERVICE/guests/create",
		 * userInput1,AuthRequest.class); Assert.notNull(authRequest,
		 * "Failed to register user. Please try again later");
		 * 
		 * String accessToken = jwt.generate(authRequest, "ACCESS"); String refreshToken
		 * = jwt.generate(authRequest, "REFRESH");
		 * 
		 * return new AuthResponse(accessToken, refreshToken);
		 */
    	
    	return null;
    }
	
}
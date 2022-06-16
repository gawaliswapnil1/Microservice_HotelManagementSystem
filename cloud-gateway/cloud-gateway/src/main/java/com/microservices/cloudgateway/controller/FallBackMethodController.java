package com.microservices.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FallBackMethodController {

	
	
	final String MESSEGE="Service is taking longer than Expected. Please try again later";
	
	@GetMapping("/guestServiceFallBack")
	public String guestServiceFallBackMethod() {
		return "Guest "+MESSEGE;
	}
	@GetMapping("/hotelServiceFallBack")
	public String hotelServiceFallBackMethod() {
		return "Hotel "+MESSEGE;
	}
	@GetMapping("/reservationServiceFallBack")
	public String reservationServiceFallBack() {
		return "Reservation "+MESSEGE;
	}
	
	@GetMapping("/auth-fallback")
    public String authFallback() {
        return "Auth service is not available";
    }
}



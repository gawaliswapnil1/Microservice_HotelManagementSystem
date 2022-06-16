package com.epam.reservation.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.reservation.model.Guest;



@FeignClient(name="Guest-Service")
public interface GuestServiceClient {

	@GetMapping("/guests/findGuestById/{Id}")
	public Optional<Guest> getGuest(@PathVariable("Id") String Id);
	
	@GetMapping("/guests/all")
	public List<Guest> getGuest();
}

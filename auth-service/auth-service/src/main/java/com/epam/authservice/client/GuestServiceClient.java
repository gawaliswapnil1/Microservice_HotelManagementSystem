package com.epam.authservice.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.authservice.entities.Guest;



@FeignClient(name="Guest-Service")
public interface GuestServiceClient {

	@GetMapping("/guests/findGuestByUserNameAndPassword")
	public Optional<Guest> findGuestByUserNameAndPassword(@RequestBody Guest guest);
	
	
}

package com.epam.guest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.guest.model.Guest;
import com.epam.guest.service.GuestService;

@RestController
@RequestMapping("/guests")
public class GuestController {

	@Autowired
	private GuestService guestService;

	@GetMapping("/all")
	public ResponseEntity<List<Guest>> getAllGust() {
		return ResponseEntity.ok(guestService.getAllGuests());
	}
	
	@PostMapping("/create")
	ResponseEntity<Guest> create(@RequestBody Guest guest) {
	    return new ResponseEntity(guestService.saveGuest(guest), HttpStatus.CREATED);
	}
	
	@GetMapping("/findGuestById/{Id}")
	ResponseEntity<Optional<Guest>> findGuestById(@PathVariable Integer Id){
		return ResponseEntity.ok(guestService.findGuestById(Id));
	}

	@DeleteMapping("/deleteGuestById/{Id}")
	ResponseEntity<Optional<Guest>> deleteGuestById(@PathVariable Integer Id){
		return new ResponseEntity(guestService.deleteGuestById(Id), HttpStatus.GONE);
	}
	
	@PutMapping("/updateGuest/{Id}")
	ResponseEntity<Guest> updateGuest(@PathVariable Integer Id,@RequestBody Guest guest){
		return ResponseEntity.ok(guestService.updateGuest(Id,guest));
	}
	
	@PostMapping("/findGuestByUserNameAndPassword")
	ResponseEntity<Optional<Guest>> findGuestByUserNameAndPassword(@RequestBody Guest guest){
		return ResponseEntity.ok(guestService.findGuestByUserNameAndPassword(guest));
	}

}

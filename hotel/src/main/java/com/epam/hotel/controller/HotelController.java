package com.epam.hotel.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.epam.hotel.dto.HotelDto;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.service.CategoryService;
import com.epam.hotel.service.HotelService;
import com.epam.hotel.service.RoomService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomService rooomService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<HotelDto>> getAllHotels() {
		return ResponseEntity.ok(hotelService.getAllHotels());
	}
	
	@PostMapping("/create")
	ResponseEntity<HotelDto> createHotel(@RequestBody Hotel hotel) {
	    return new ResponseEntity(hotelService.saveHotel(hotel), HttpStatus.CREATED);
	}
	
	@GetMapping("/findHotelById/{Id}")
	ResponseEntity<Optional<HotelDto>> findHotelById(@PathVariable Integer Id){
		return ResponseEntity.ok(hotelService.findHotelById(Id));
	}

	@DeleteMapping("/deleteHotelById/{Id}")
	ResponseEntity<Optional<HotelDto>> deleteHotelById(@PathVariable Integer Id){
		return new ResponseEntity(hotelService.deleteHotelById(Id), HttpStatus.GONE);
	}
	
	@PutMapping("/updateHotel/{Id}")
	ResponseEntity<HotelDto> updateHotel(@PathVariable Integer Id,@RequestBody Hotel hotel){
		return ResponseEntity.ok(hotelService.updateHotel(Id,hotel));
	}
}

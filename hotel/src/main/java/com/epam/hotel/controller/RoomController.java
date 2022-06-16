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

import com.epam.hotel.dto.RoomDto;
import com.epam.hotel.model.Room;
import com.epam.hotel.service.RoomService;



@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@GetMapping("/all")
	public ResponseEntity<List<RoomDto>> getAllGust() {
		return ResponseEntity.ok(roomService.getAllRooms());
	}
	
	@PostMapping("/create")
	ResponseEntity<RoomDto> create(@RequestBody Room room) {
	    return new ResponseEntity(roomService.saveRoom(room), HttpStatus.CREATED);
	}
	
	@GetMapping("/findRoomById/{Id}")
	ResponseEntity<Optional<RoomDto>> findRoomById(@PathVariable Integer Id){
		return ResponseEntity.ok(roomService.findRoomById(Id));
	}

	@DeleteMapping("/deleteRoomById/{Id}")
	ResponseEntity<Optional<RoomDto>> deleteRoomById(@PathVariable Integer Id){
		return new ResponseEntity(roomService.deleteRoomById(Id), HttpStatus.GONE);
	}
	
	@PutMapping("/updateRoom/{Id}")
	ResponseEntity<RoomDto> updateRoom(@PathVariable Integer Id,@RequestBody Room room){
		return ResponseEntity.ok(roomService.updateRoom(Id,room));
	}
	
	@GetMapping("/findRoomAndHotel/{hotelId}")
	ResponseEntity<List<RoomDto>> findRoomAndHotel(@PathVariable Integer hotelId){
		return ResponseEntity.ok(roomService.findRoomAndHotel(hotelId));
	}
	
	
}

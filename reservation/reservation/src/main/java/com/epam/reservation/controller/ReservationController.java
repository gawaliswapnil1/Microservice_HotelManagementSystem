package com.epam.reservation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
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

import com.epam.reservation.model.Guest;
import com.epam.reservation.model.Reservation;
import com.epam.reservation.model.Room;
import com.epam.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/all")
	public ResponseEntity<List<Reservation>> getAllReservation() {
		return ResponseEntity.ok(reservationService.getAllReservations());
	}

	@GetMapping("/guests/all")
	public ResponseEntity<List<Guest>> getAllGust() {
		return ResponseEntity.ok(reservationService.getAllGuests());
	}

	
	
	@PostMapping("/createBooking")
	ResponseEntity<String> create(@RequestBody Reservation reservation) {
		try {
			Reservation savedReservation = reservationService.saveReservation(reservation);
			return new ResponseEntity(reservationService.getReservationDetails(savedReservation.getReservationId(),savedReservation.getStartDate(),savedReservation.getEndDate()), HttpStatus.CREATED);
		}
		catch(Exception ex) {
			
		}
		return null;
	}

	@GetMapping("/findReservationById/{Id}")
	ResponseEntity<Optional<Reservation>> findReservationById(@PathVariable Integer Id) {
		return ResponseEntity.ok(reservationService.findReservationById(Id));
	}

	@DeleteMapping("/deleteReservationById/{Id}")
	ResponseEntity<Optional<Reservation>> deleteReservationById(@PathVariable Integer Id) {
		return new ResponseEntity(reservationService.deleteReservationById(Id), HttpStatus.GONE);
	}

	@PutMapping("/updateReservation/{Id}")
	ResponseEntity<Reservation> updateReservation(@PathVariable Integer Id, @RequestBody Reservation reservation) {
		return ResponseEntity.ok(reservationService.updateReservation(Id, reservation));
	}

	@PostMapping("/findRoomAvailability")
	 ResponseEntity<List<Room>> findRoomAvailability(@RequestBody String inputData) {
		JSONObject input=new JSONObject(inputData);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
		LocalDate startDate = LocalDate.parse((String)input.get("startDate"), formatter);
		LocalDate endDate = LocalDate.parse((String)input.get("endDate"), formatter);
		Integer hotelId=input.getInt("hotelId");
		Integer roomId=input.getInt("roomId");
		return new ResponseEntity(reservationService.findRoomAvailability(hotelId,roomId,startDate, endDate), HttpStatus.OK);
		
	}
	
	
}

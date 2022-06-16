package com.epam.reservation.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.reservation.Exception.NoDataFoundException;
import com.epam.reservation.Exception.ReservationNotFoundException;
import com.epam.reservation.client.GuestServiceClient;
import com.epam.reservation.client.HotelServiceClient;
import com.epam.reservation.model.Guest;
import com.epam.reservation.model.Hotel;
import com.epam.reservation.model.Reservation;
import com.epam.reservation.model.Room;
import com.epam.reservation.model.RoomDto;
import com.epam.reservation.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private GuestServiceClient guestClient;

	@Autowired
	private HotelServiceClient hotelClient;

	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}

	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public Optional<Reservation> findReservationById(Integer Id) {
		Optional<Reservation> reservation = reservationRepository.findById(Id);

		if (reservation.isEmpty()) {
			throw new ReservationNotFoundException(Id);
		} else {
			return reservation;
		}
	}

	public String deleteReservationById(Integer Id) {

		try {
			reservationRepository.deleteById(Id);
			return "SUCCESS";
		} catch (NoDataFoundException ex) {
			log.debug("Data not found for ID " + ex.getMessage());
		}

		return "FAILED!!";
	}

	public Reservation updateReservation(Integer Id, Reservation reservation) {

		Reservation reservationDB = new Reservation();
		Optional<Reservation> reservationFound = reservationRepository.findById(Id);
		if (reservationFound.isPresent()) {

			reservationDB = reservationFound.get();

			if (Objects.nonNull(reservation.getStartDate())) {
				reservationDB.setStartDate(reservation.getStartDate());
			}

			if (Objects.nonNull(reservation.getEndDate())) {
				reservationDB.setEndDate(reservation.getEndDate());
			}

		}
		return reservationRepository.save(reservationDB);
	}

	public List<Guest> getAllGuests() {
		return guestClient.getGuest();
	}

	public List<RoomDto> findRoomAvailability(Integer hotelId, Integer roomId, LocalDate startDate, LocalDate endDate) {
		// get all available rooms for hotelId and roomId from hotel client
		List<RoomDto> availableRooms =hotelClient.findRoomAndHotel(hotelId);
		Set<Integer> filteredRoomId=availableRooms.stream().map(RoomDto::getRoomId).collect(Collectors.toSet());
		
		// query if dates is outside of existing reservation
		Date startDateSql = new Date(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
		Date endDateSql = new Date(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());

		List<Reservation> reservations = reservationRepository.findRoomAvailabilityByDate(hotelId, roomId, startDateSql,
				endDateSql);
		List<Integer> reservationRoomsID = reservations.stream().map(Reservation::getRoomId).collect(Collectors.toList());
		filteredRoomId.removeAll(reservationRoomsID);
		
		 
		List<RoomDto> rooms =availableRooms.stream().filter(a->filteredRoomId.stream().anyMatch(r->r==a.getRoomId())).collect(Collectors.toList());
		return rooms;
	}

	public String getReservationDetails(Integer id,Date startDate,Date endDate) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		JSONObject reservationDetails = new JSONObject();
		if (reservation.isPresent()) {
			Integer guestId = reservation.get().getGuestId();
			Integer hotelId = reservation.get().getHotelId();
			Integer roomId = reservation.get().getRoomId();

			Optional<Guest> guest = guestClient.getGuest(Integer.toString(guestId));
			if (guest.isPresent()) {
				getGuestDetails(guest.get(), reservationDetails);
			}

			getHotelAndRoomDetails(hotelId, roomId, reservationDetails,startDate,endDate);

		}
		return reservationDetails.toString();
	}

	private void getHotelAndRoomDetails(Integer hotelId, Integer roomId, JSONObject reservationDetails,Date startDate,Date endDate) {

		JSONObject jsonHotel = new JSONObject();
		
		try {
			Optional<Hotel> hotel = hotelClient.getHotelbyId(Integer.toString(hotelId));
			if (hotel.isPresent()) {
				jsonHotel.put("Hotel Name", hotel.get().getName());
				jsonHotel.put("Description", hotel.get().getDescription());
			}
			
			 Optional<Room> room = hotelClient.getRoombyId(Integer.toString(roomId));
			
			if(room.isPresent()) {
				jsonHotel.put("Room Name",room.get().getName());
				jsonHotel.put("Room Description",room.get().getDescription());
				jsonHotel.put("Room Type",room.get().getRoomType());
				jsonHotel.put("Rate",room.get().getRoomRate());
				jsonHotel.put("StartDate", startDate.toString());
				jsonHotel.put("endDate", endDate.toString());
			}
			reservationDetails.put("Reservation Details", jsonHotel);
			
		} catch (NoDataFoundException ex) {
			log.debug(ex.getMessage());
		}
	}

	private void getGuestDetails(Guest guest, JSONObject reservationDetails) {
		try {
			JSONObject jsonGuest = new JSONObject();
			jsonGuest.put("FirstName", guest.getFirstName());
			jsonGuest.put("LastName", guest.getLastName());
			jsonGuest.put("Email ", guest.getEmail());
			jsonGuest.put("Address", guest.getAddress());
			reservationDetails.put("Guest Details", jsonGuest);
		} catch (NoDataFoundException ex) {
			log.debug(ex.getMessage());
		}
	}
	
	
}

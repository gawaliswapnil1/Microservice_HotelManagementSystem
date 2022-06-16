package com.epam.reservation.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.reservation.model.Guest;
import com.epam.reservation.model.Reservation;
import com.epam.reservation.repository.ReservationRepository;
import com.epam.reservation.service.ReservationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestReservationService {

	@Autowired
	private ReservationService reservationService;

	@MockBean
	private ReservationRepository reservationRepository;
	
	Reservation reservationInput = new Reservation(1,4,5,6,new Date(2020,05,12),new Date(2020,05,14),1000);
	
	@BeforeEach
	void setUp() {
		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationInput);
	}
	
	@AfterEach
	void tearDown() {
		reservationRepository.deleteAll();
	}
	
	@Test
	void createReservation() {

		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationInput);
		
		Reservation savedReservation = reservationService.saveReservation(reservationInput);
		assertEquals(reservationInput.getStartDate(), savedReservation.getStartDate());
	}

	
	@Test
	void findReservationById() {
		
		Reservation savedReservation = reservationService.saveReservation(reservationInput);
		
		when(reservationRepository.findById(savedReservation.getReservationId())).thenReturn(Optional.of(savedReservation));
		Optional<Reservation> reservationFound = reservationService.findReservationById(savedReservation.getReservationId());
		assertEquals(reservationInput.getStartDate(), reservationFound.get().getStartDate());
	}

	@Test
	void deleteReservationById() {
		Reservation savedReservation = reservationService.saveReservation(reservationInput);
		
		String isSuccessful = reservationService.deleteReservationById(savedReservation.getReservationId());
		assertEquals("SUCCESS", isSuccessful);
	}

	@Test
	void updateReservation() {
		Reservation savedReservation = reservationService.saveReservation(reservationInput);

		when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);
		savedReservation.setEndDate(new Date(2022,06,06));
		Reservation updateReservation = reservationService.updateReservation(savedReservation.getReservationId(), savedReservation);

		assertEquals(reservationInput.getEndDate().getDay(), updateReservation.getEndDate().getDay());

	}

	@Test 
	void findRoomAvailability() {
		Reservation savedReservation = reservationService.saveReservation(reservationInput);
		List<Reservation> reservations=new ArrayList<>();
		reservations.add(savedReservation);
		when(reservationRepository.findRoomAvailabilityByDate(1,1,new Date(2022, 5, 6),new Date(2022,5,8)))
		.thenReturn(reservations);
		
		assertEquals(1, reservations.size());
	}

	/*
	 * @Test void getReservationDetails() { Reservation savedReservation =
	 * reservationService.saveReservation(reservationInput);
	 * when(reservationRepository.findById(savedReservation.getReservationId())).
	 * thenReturn(Optional.of(savedReservation));
	 * 
	 * }
	 */
}

package com.epam.reservation.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.reservation.controller.ReservationController;
import com.epam.reservation.model.Reservation;
import com.epam.reservation.model.RoomDto;
import com.epam.reservation.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
class TestReservationController {



	@Autowired
	MockMvc mockMvc;

	@MockBean
	private ReservationService reservationService;

	Reservation reservation = new Reservation(1,2,4,3,new Date(2020,05,12),new Date(2020,05,14),1000);

	@Test
	void getAllReservations() throws Exception {

		List<Reservation> reservationList = new ArrayList<Reservation>();
		LocalDate StartDate = LocalDate.of(2020,05,14);
		LocalDate EndDate=LocalDate.of(2020,05,15);
		reservationList.add(new Reservation(1,2,33,3,new Date(2021,10,02),new Date(2021,10,03),1000));
		reservationList.add(new Reservation(2,44,45,45,new Date(2021,10,02),new Date(2020,10,04),2000));
		when(reservationService.getAllReservations()).thenReturn(reservationList);

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/all")).andExpect(status().isOk());
	}

	@Test
	void fingReservationById() throws Exception {

		when(reservationService.findReservationById(reservation.getReservationId())).thenReturn(Optional.of(reservation));

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/findReservationById/" + Integer.toString(reservation.getReservationId())))
				.andExpect(status().isOk());

	}

	@Test
	void createReservation() throws Exception {

		when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);
		ObjectMapper objectMapper = new ObjectMapper();
		String reservationJSON = objectMapper.writeValueAsString(reservation);

		ResultActions result = mockMvc
				.perform(post("/reservations/createBooking").contentType(MediaType.APPLICATION_JSON).content(reservationJSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void deleteReservation() throws Exception {

		when(reservationService.deleteReservationById(reservation.getReservationId())).thenReturn("SUCCESS");
		mockMvc.perform(delete("/reservations/deleteReservationById/" + Integer.toString(reservation.getReservationId())))
				.andExpect(status().isGone());
	}

	@Test
	void updateReservation() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String reservationJSON = objectMapper.writeValueAsString(reservation);
		
		when(reservationService.updateReservation(reservation.getReservationId(),reservation)).thenReturn(reservation);
		
		mockMvc.perform(put("/reservations/updateReservation/"+reservation.getReservationId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(reservationJSON))
				.andExpect(status().isOk());
	}


	@Test
	void findRoomForGivenDates() throws Exception {
		
		Date startDate=new Date(2022, 6, 7);
		Date endDate=new Date(2022, 6, 8);
		JSONObject inputData=new JSONObject();
		inputData.put("StartDate", startDate);
		inputData.put("EndDate", endDate);
		List<RoomDto> rooms=new ArrayList<>();
		when(reservationService.findRoomAvailability(1,2,LocalDate.of(2022, 6, 7),LocalDate.of(2022, 6, 8))).thenReturn(rooms);
		
		mockMvc.perform(post("/reservations/findRoomAvailability")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(inputData.toString()))
				.andExpect(status().isOk());
		
	}
	
}

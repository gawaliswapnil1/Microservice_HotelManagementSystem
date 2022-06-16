package com.epam.guest.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.epam.guest.controller.GuestController;
import com.epam.guest.model.Guest;
import com.epam.guest.service.GuestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GuestController.class)
class TestGuestController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private GuestService guestService;

	Guest guest = Guest.builder().guestId(1)
				.firstName("Swapnil")
				.lastName("Gawali")
				.email("xyz@gmail.com")
				.address("Ahmednagar")
				.build();
			;//new Guest(1, "Swapnil", "Gawali", "xyx@gmail.com", "address", "");

	@Test
	void getAllGuests() throws Exception {

		List<Guest> guestList = new ArrayList<Guest>();
		guestList.add(Guest.builder().guestId(1)
				.firstName("Swapnil")
				.lastName("Gawali")
				.email("xyz@gmail.com")
				.address("Ahmednagar")
				.build());
		guestList.add(Guest.builder().guestId(2)
				.firstName("Nilesh")
				.lastName("walake")
				.email("nilesh@gmail.com")
				.address("pune")
				.build());
		when(guestService.getAllGuests()).thenReturn(guestList);

		mockMvc.perform(MockMvcRequestBuilders.get("/guests/all")).andExpect(status().isOk());
	}

	@Test
	void fingGuestById() throws Exception {

		when(guestService.findGuestById(guest.getGuestId())).thenReturn(Optional.of(guest));

		mockMvc.perform(MockMvcRequestBuilders.get("/guests/findGuestById/" + Integer.toString(guest.getGuestId())))
				.andExpect(status().isOk());

	}

	@Test
	void createGuest() throws Exception {

		when(guestService.saveGuest(any(Guest.class))).thenReturn(guest);
		ObjectMapper objectMapper = new ObjectMapper();
		String guestJSON = objectMapper.writeValueAsString(guest);

		ResultActions result = mockMvc
				.perform(post("/guests/create").contentType(MediaType.APPLICATION_JSON).content(guestJSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void deleteGuest() throws Exception {

		when(guestService.deleteGuestById(guest.getGuestId())).thenReturn("SUCCESS");
		mockMvc.perform(delete("/guests/deleteGuestById/" + Integer.toString(guest.getGuestId())))
				.andExpect(status().isGone());
	}

	@Test
	void updateGuest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String guestJSON = objectMapper.writeValueAsString(guest);
		
		when(guestService.updateGuest(guest.getGuestId(),guest)).thenReturn(guest);
		
		mockMvc.perform(put("/guests/updateGuest/"+guest.getGuestId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(guestJSON))
				.andExpect(status().isOk());
	}

}

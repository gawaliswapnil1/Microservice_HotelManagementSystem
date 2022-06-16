package com.epam.hotel.controllertest;

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

import com.epam.hotel.controller.HotelController;
import com.epam.hotel.dto.HotelDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.service.CategoryService;
import com.epam.hotel.service.HotelService;
import com.epam.hotel.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelController.class)
class TestHotelController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private HotelService hotelService;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private RoomService roomService;

	 Category category=new Category(1, "5 Star",null); 
	 HotelDto hotelDto = new HotelDto(1,"Royal Oak","5 start hotel");

	@Test
	void getAllHotels() throws Exception {

		List<HotelDto> hotelList = new ArrayList<HotelDto>();
		hotelList.add(new HotelDto(1, "Royal Oak", "5 start hotel"));
		hotelList.add(new HotelDto(2, "Royal Oak", "5 start hotel"));
		when(hotelService.getAllHotels()).thenReturn(hotelList);

		mockMvc.perform(MockMvcRequestBuilders.get("/hotels/all")).andExpect(status().isOk());
	}

	@Test
	void fingHotelById() throws Exception {

		when(hotelService.findHotelById(hotelDto.getHotelId())).thenReturn(Optional.of(hotelDto));

		mockMvc.perform(MockMvcRequestBuilders.get("/hotels/findHotelById/" + Integer.toString(hotelDto.getHotelId())))
				.andExpect(status().isOk());

	}

	@Test
	void createHotel() throws Exception {

		when(hotelService.saveHotel(any(Hotel.class))).thenReturn(hotelDto);
		ObjectMapper objectMapper = new ObjectMapper();
		String hotelJSON = objectMapper.writeValueAsString(hotelDto);

		ResultActions result = mockMvc
				.perform(post("/hotels/create").contentType(MediaType.APPLICATION_JSON).content(hotelJSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void deleteHotel() throws Exception {

		when(hotelService.deleteHotelById(hotelDto.getHotelId())).thenReturn("SUCCESS");
		mockMvc.perform(delete("/hotels/deleteHotelById/" + Integer.toString(hotelDto.getHotelId())))
				.andExpect(status().isGone());
	}

	@Test
	void updateHotel() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String hotelJSON = objectMapper.writeValueAsString(hotelDto);

		when(hotelService.updateHotel(hotelDto.getHotelId(),convertDtoToEntity(hotelDto))).thenReturn(hotelDto);

		mockMvc.perform(put("/hotels/updateHotel/" + hotelDto.getHotelId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(hotelJSON)).andExpect(status().isOk());
	}
	

	private HotelDto convertEntityToDto(Hotel hotel) {
		HotelDto hotelDTO=new HotelDto();
		hotelDTO.setHotelId(hotel.getHotelId());
		hotelDTO.setName(hotel.getName());
		hotelDTO.setDescription(hotel.getDescription());
		return hotelDTO;
	}
	

	private Hotel convertDtoToEntity(HotelDto hotelDto) {
		Hotel hotel=new Hotel();
		hotel.setName(hotelDto.getName());
		hotel.setDescription(hotelDto.getDescription());
		hotel.setHotelId(hotelDto.getHotelId());
		return hotel;
	}
}

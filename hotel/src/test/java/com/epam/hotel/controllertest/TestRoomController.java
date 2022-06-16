package com.epam.hotel.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.epam.hotel.controller.RoomController;
import com.epam.hotel.dto.HotelDto;
import com.epam.hotel.dto.RoomDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.model.Room;
import com.epam.hotel.service.HotelService;
import com.epam.hotel.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
public class TestRoomController {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private HotelService HotelService;

	@MockBean
	private RoomService roomService;

	 Category category=new Category(1, "5 Star",null);

	private Hotel hotel = new Hotel(1, "Royal Oak", "Manali", category,null);

	private RoomDto roomDto = new RoomDto(1, "Delux", "Spacious withA/C", "WithAC", 100);

	@Test
	void getAllRooms() throws Exception {

		List<RoomDto> roomList = new ArrayList<>();
		roomList.add(new RoomDto(1, "Delux", "Spacious withA/C", "WithAC", 200));
		roomList.add(new RoomDto(2, "Super Delux", "Spacious with A/C and Jaccuzi", "WithAC", 300));
		when(roomService.getAllRooms()).thenReturn(roomList);

		mockMvc.perform(MockMvcRequestBuilders.get("/rooms/all")).andExpect(status().isOk());
	}

	@Test
	void fingRoomById() throws Exception {

		when(roomService.findRoomById(roomDto.getRoomId())).thenReturn(Optional.of(roomDto));

		mockMvc.perform(MockMvcRequestBuilders.get("/rooms/findRoomById/" + Integer.toString(roomDto.getRoomId())))
				.andExpect(status().isOk());

	}

	@Test
	void createRoom() throws Exception {

		when(roomService.saveRoom(any(Room.class))).thenReturn(roomDto);
		ObjectMapper objectMapper = new ObjectMapper();
		String roomJSON = objectMapper.writeValueAsString(roomDto);

		ResultActions result = mockMvc
				.perform(post("/rooms/create").contentType(MediaType.APPLICATION_JSON).content(roomJSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void deleteRoom() throws Exception {

		when(roomService.deleteRoomById(roomDto.getRoomId())).thenReturn("SUCCESS");
		mockMvc.perform(delete("/rooms/deleteRoomById/" + Integer.toString(roomDto.getRoomId())))
				.andExpect(status().isGone());
	}

	@Test
	void updateRoom() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String roomJSON = objectMapper.writeValueAsString(roomDto);

		when(roomService.updateRoom(roomDto.getRoomId(),convertDtoToEntity(roomDto))).thenReturn(roomDto);

		mockMvc.perform(put("/rooms/updateRoom/" + roomDto.getRoomId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(roomJSON)).andExpect(status().isOk());
	}
	
	@Test
	void findRoomAndHotel() throws Exception {
		List<RoomDto> roomList = new ArrayList<>();
		roomList.add(new RoomDto(1, "Delux", "Spacious withA/C", "WithAC", 200));
		roomList.add(new RoomDto(2, "Super Delux", "Spacious with A/C and Jaccuzi", "WithAC", 300));
		when(roomService.findRoomAndHotel(hotel.getHotelId())).thenReturn(roomList);
		mockMvc.perform(MockMvcRequestBuilders.get("/rooms/findRoomAndHotel/"+hotel.getHotelId())).andExpect(status().isOk());
	}
	
	private RoomDto convertEntityToDto(Room room) {
		RoomDto roomDto=new RoomDto();
		roomDto.setRoomId(room.getRoomId());
		roomDto.setName(room.getName());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomRate(room.getRoomRate());
		roomDto.setDescription(room.getDescription());
		return roomDto;
	}

	private Room convertDtoToEntity(RoomDto roomDto) {
		Room room=new Room();
		room.setRoomId(roomDto.getRoomId());
		room.setName(roomDto.getName());
		room.setRoomType(roomDto.getRoomType());
		room.setRoomRate(roomDto.getRoomRate());
		room.setDescription(roomDto.getDescription());
		return room;
	}
}

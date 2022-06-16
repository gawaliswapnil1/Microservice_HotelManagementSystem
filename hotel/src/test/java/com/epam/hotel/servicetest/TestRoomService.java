package com.epam.hotel.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.hotel.Exception.RoomNotFoundException;
import com.epam.hotel.dto.RoomDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.Status;
import com.epam.hotel.repository.RoomRepository;
import com.epam.hotel.service.RoomService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestRoomService {

	@Autowired
	private RoomService roomService;

	@MockBean
	private RoomRepository roomRepository;

	Category category = new Category(1, "5 Start",null);

	Hotel hotel = new Hotel(1000, "Test Hotel", "Test Desciption of Hotel", category,null);

	Room roomInput = new Room(10000, "Delux Room", "Delux room Description", "Spacious", 500,Status.Available, hotel);

	@BeforeEach
	void setUp() {
		when(roomRepository.save(any(Room.class))).thenReturn(roomInput);
	}

	@AfterEach
	void tearDown() {
		roomRepository.deleteAll();
	}

	@Test
	void createRoom() {

		when(roomRepository.save(any(Room.class))).thenReturn((roomInput));

		RoomDto savedRoom = roomService.saveRoom(roomInput);
		assertEquals(roomInput.getName(), savedRoom.getName());
	}

	void getAllRooms() {
		List<RoomDto> roomList = new ArrayList<>();

		roomService.saveRoom(new Room(10000, "Delux Room", "Delux room Description", "Spacious", 500, Status.Available, hotel));
		roomService.saveRoom(new Room(10001, "Business Room", "Business room Description", "Spacious", 500,Status.Available, hotel));
		roomService.saveRoom(new Room(10002, "Delux Suit Room", "Suite room Description", "Spacious", 600,Status.Available, hotel));

		List<RoomDto> roomOutput = roomService.getAllRooms();
		assertEquals(3, roomOutput.size());
	}

	@Test
	void findRoomById() throws RoomNotFoundException {

		Room savedRoom =convertDtoToEntity(roomService.saveRoom(roomInput));

		when(roomRepository.findById(savedRoom.getRoomId())).thenReturn(Optional.of(savedRoom));
		Optional<RoomDto> roomFound = roomService.findRoomById(savedRoom.getRoomId());
		assertEquals(roomInput.getName(), roomFound.get().getName());
	}

	@Test
	void deleteRoomById() {
		RoomDto savedRoomDto = roomService.saveRoom(roomInput);

		String isSuccessful = roomService.deleteRoomById(savedRoomDto.getRoomId());
		assertEquals("SUCCESS", isSuccessful);
	}

	@Test
	void updateRoom() {
		Room savedRoom =convertDtoToEntity(roomService.saveRoom(roomInput));

		when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);
		savedRoom.setName("new details");
		RoomDto updateRoomDto = roomService.updateRoom(savedRoom.getRoomId(),savedRoom);

		assertEquals(roomInput.getName(), updateRoomDto.getName());

	}
	@Test
	void findRoomAndHotel() throws Exception {
		Room savedRoom =convertDtoToEntity(roomService.saveRoom(roomInput));
		savedRoom.setHotel(hotel);
		List<Room> roomList = new ArrayList<>();
		roomList.add(savedRoom);
		when(roomRepository.findHotelAndRoomById(savedRoom.getHotel().getHotelId())).thenReturn(roomList);
		List<RoomDto> roomFound = roomService.findRoomAndHotel(savedRoom.getHotel().getHotelId());
		assertEquals(roomInput.getName(), roomFound.get(0).getName());
	}

	private RoomDto convertEntityToDto(RoomDto room) {
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

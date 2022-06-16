package com.epam.hotel.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import com.epam.hotel.Exception.NoDataFoundException;
import com.epam.hotel.Exception.RoomNotFoundException;
import com.epam.hotel.dto.RoomDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.model.Room;
import com.epam.hotel.repository.HotelRepository;
import com.epam.hotel.repository.RoomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HotelRepository hotelRepository;

	public List<RoomDto> getAllRooms() {
		return convertEntityToDto(roomRepository.findAll());
	}

	public RoomDto saveRoom(Room room) {

		Hotel hotelInput = room.getHotel();

		if (hotelInput != null && hotelInput.getHotelId() != null) {
			Optional<Hotel> hotel = hotelRepository.findById(hotelInput.getHotelId());
			Hotel savedHotel;
			if (hotel.isEmpty()) {
				savedHotel = hotelRepository.save(hotelInput);
			} else {
				savedHotel = hotel.get();
			}
			room.setHotel(savedHotel);
		}

		return convertEntityToDto(roomRepository.save(room));
	}

	public Optional<RoomDto> findRoomById(Integer Id) {
		Optional<Room> roomFound = roomRepository.findById(Id);

		if (roomFound.isEmpty()) {
			throw new RoomNotFoundException(Id);
		} else {
			return Optional.of(convertEntityToDto(roomFound.get()));
		}
	}

	public String deleteRoomById(Integer Id) {

		try {
			roomRepository.deleteById(Id);
			return "SUCCESS";
		} catch (NoDataFoundException ex) {
			log.debug("Data not found for ID " + ex.getMessage());
		}

		return "FAILED!!";
	}

	public RoomDto updateRoom(Integer Id, Room room) {

		Room roomDB = new Room();
		Optional<Room> roomFound = roomRepository.findById(Id);
		if (roomFound.isPresent()) {

			roomDB = roomFound.get();

			if (Objects.nonNull(room.getName()) && !"".equalsIgnoreCase(room.getName())) {
				roomDB.setName(room.getName());
			}

			if (Objects.nonNull(room.getDescription()) && !"".equalsIgnoreCase(room.getDescription())) {
				roomDB.setDescription(room.getDescription());
			}

			if (Objects.nonNull(room.getRoomType()) && !"".equalsIgnoreCase(room.getRoomType())) {
				roomDB.setRoomType(room.getRoomType());
			}

		}
		return convertEntityToDto(roomRepository.save(roomDB));
	}

	public List<RoomDto> findRoomAndHotel(Integer hotelId) {
		return convertEntityToDto(roomRepository.findHotelAndRoomById(hotelId));
	}

	private List<RoomDto> convertEntityToDto(List<Room> rooms) {
		return rooms.stream().filter(Objects::nonNull).map(r -> convertEntityToDto(r)).collect(Collectors.toList());
	}

	private RoomDto convertEntityToDto(Room room) {
		RoomDto roomDto = new RoomDto();
		roomDto.setRoomId(room.getRoomId());
		roomDto.setName(room.getName());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomRate(room.getRoomRate());
		roomDto.setDescription(room.getDescription());
		return roomDto;
	}

	private Room convertDtoToEntity(RoomDto roomDto) {
		Room room = new Room();
		room.setRoomId(roomDto.getRoomId());
		room.setName(roomDto.getName());
		room.setRoomType(roomDto.getRoomType());
		room.setRoomRate(roomDto.getRoomRate());
		room.setDescription(roomDto.getDescription());
		return room;
	}

	
}

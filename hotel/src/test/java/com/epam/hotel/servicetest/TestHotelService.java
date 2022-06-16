package com.epam.hotel.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.hotel.Exception.HotelNotFoundException;
import com.epam.hotel.dto.HotelDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;
import com.epam.hotel.repository.HotelRepository;
import com.epam.hotel.service.HotelService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestHotelService {

	@Autowired
	private HotelService hotelService;

	@MockBean
	private HotelRepository hotelRepository;

	Category category = new Category(1, "5 Start",null);

	Hotel hotelInput = new Hotel(10000, "Royal oak", "Description", category,null);

	@BeforeEach
	void setUp() {
		when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelInput);
	}

	@AfterEach
	void tearDown() {
		hotelRepository.deleteAll();
	}

	@Test
	void createHotel() {

		when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelInput);

		HotelDto savedHotel = hotelService.saveHotel(hotelInput);
		assertEquals(hotelInput.getName(), savedHotel.getName());
	}

	void getAllHotels() {
		List<HotelDto> hotelList = new ArrayList<>();

		hotelService.saveHotel(new Hotel(10000, "Royal oak", "Description",null,null));
		hotelService.saveHotel(new Hotel(10001, "Radisson ", "Description",null,null));
		hotelService.saveHotel(new Hotel(10002, "Le meridian", "Description",null,null));

		List<HotelDto> hotelOutput = hotelService.getAllHotels();
		assertEquals(3, hotelOutput.size());
	}

	@Test
	void findHotelById() throws HotelNotFoundException {

		HotelDto savedHotel = hotelService.saveHotel(hotelInput);

		when(hotelRepository.findById(savedHotel.getHotelId())).thenReturn(Optional.of(convertDtoToEntity(savedHotel)));
		Optional<HotelDto> hotelFound = hotelService.findHotelById(savedHotel.getHotelId());
		assertEquals(hotelInput.getName(), hotelFound.get().getName());
	}

	@Test
	void deleteHotelById() {
		HotelDto savedHotel = hotelService.saveHotel(hotelInput);

		String isSuccessful = hotelService.deleteHotelById(savedHotel.getHotelId());
		assertEquals("SUCCESS", isSuccessful);
	}

	@Test
	void updateHotel() {
		HotelDto savedHotel = hotelService.saveHotel(hotelInput);

		when(hotelRepository.save(any(Hotel.class))).thenReturn(convertDtoToEntity(savedHotel));
		savedHotel.setName("new details");
		HotelDto updateHotel = hotelService.updateHotel(savedHotel.getHotelId(),convertDtoToEntity(savedHotel));

		assertEquals(hotelInput.getName(), updateHotel.getName());

	}
	
	private List<HotelDto> convertEntiTytoDto(List<Hotel> hotels) {
		return	hotels.stream().filter(Objects::nonNull).map(t->convertEntityToDto(t)).collect(Collectors.toList());
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

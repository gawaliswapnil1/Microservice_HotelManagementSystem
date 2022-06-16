package com.epam.hotel.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.hotel.Exception.HotelNotFoundException;
import com.epam.hotel.Exception.NoDataFoundException;
import com.epam.hotel.dto.HotelDto;
import com.epam.hotel.model.Category;
import com.epam.hotel.model.Hotel;

import com.epam.hotel.repository.CategoryRepository;
import com.epam.hotel.repository.HotelRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<HotelDto> getAllHotels() {
		return convertEntityToDto(hotelRepository.findAll());
	}
	
	public Optional<HotelDto> findHotelById(Integer Id) {
		Optional<Hotel> hotel = hotelRepository.findById(Id);

		if (hotel.isEmpty()) {
			throw new HotelNotFoundException(Id);
		} else {
			return Optional.of(convertEntityToDto(hotel.get()));
		}
	}

	public HotelDto saveHotel(Hotel hotel) {
		Category categoryInput = hotel.getCategory();
		if (categoryInput != null && categoryInput.getCategoryId() != null) {
			Optional<Category> category = categoryRepository.findById(categoryInput.getCategoryId());
			Category savedCategory;
			if (category.isEmpty()) {
				savedCategory = categoryRepository.save(categoryInput);
			} else {
				savedCategory = category.get();
			}
			hotel.setCategory(savedCategory);
		}
		
		return convertEntityToDto(hotelRepository.save(hotel));
	}
	
	

	public String deleteHotelById(Integer Id) {

		try {
			hotelRepository.deleteById(Id);
			return "SUCCESS";
		} catch (NoDataFoundException ex) {
			log.debug("Data not found for ID " + ex.getMessage());
		}

		return "FAILED!!";
	}

	public HotelDto updateHotel(Integer Id, Hotel hotel) {

		Hotel hotelDB = new Hotel();
		Optional<Hotel> hotelFound = hotelRepository.findById(Id);
		if (hotelFound.isPresent()) {

			hotelDB = hotelFound.get();

			if (Objects.nonNull(hotel.getName()) && !"".equalsIgnoreCase(hotel.getName())) {
				hotelDB.setName(hotel.getName());
			}

			if (Objects.nonNull(hotel.getDescription()) && !"".equalsIgnoreCase(hotel.getDescription())) {
				hotelDB.setDescription(hotel.getDescription());
			}
						
		}
		return convertEntityToDto(hotelRepository.save(hotelDB));
	}

	private List<HotelDto> convertEntityToDto(List<Hotel> hotels) {
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

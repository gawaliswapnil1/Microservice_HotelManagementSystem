package com.epam.hotel.Exception;

public class HotelNotFoundException extends RuntimeException {

	public HotelNotFoundException(Integer Id) {
		super(String.format("Hotel with %d not found",Id));
	}


}

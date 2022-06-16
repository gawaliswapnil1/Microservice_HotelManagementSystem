package com.epam.hotel.Exception;

public class RoomNotFoundException extends RuntimeException {

	public RoomNotFoundException(Integer Id) {
		super(String.format("Room Not Found Exception", Id));
	}
}

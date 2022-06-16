package com.epam.guest.exception;

public class GuestNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public GuestNotFoundException(Integer Id) {
		super(String.format("Guest with %d not found",Id));
	}

}

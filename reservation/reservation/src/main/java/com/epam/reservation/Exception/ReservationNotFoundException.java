package com.epam.reservation.Exception;

public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException(Integer Id) {
		super(String.format("Reservation not found for %d", Id));
	}
}

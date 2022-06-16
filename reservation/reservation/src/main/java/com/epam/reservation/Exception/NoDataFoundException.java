package com.epam.reservation.Exception;

public class NoDataFoundException extends RuntimeException {

	public NoDataFoundException() {
		super("No data Found");
	}
}

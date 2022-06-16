package com.epam.hotel.Exception;

public class NoDataFoundException extends RuntimeException{

	public NoDataFoundException() {

        super("No data found");
    }
}

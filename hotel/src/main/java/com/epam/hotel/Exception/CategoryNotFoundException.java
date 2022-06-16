package com.epam.hotel.Exception;

public class CategoryNotFoundException extends Exception {

	public CategoryNotFoundException(Integer Id) {
		super(String.format("Category no found", Id));
	}
}

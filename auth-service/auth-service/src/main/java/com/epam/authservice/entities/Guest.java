package com.epam.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guest {
	
	
	private Integer guestId;

	private String firstName;
	
	
	private String lastName;
	
	
	private String email;
	
	
	private String address;
	
	
	private String details;
	
	private String role;
	
	
	private String password;
}

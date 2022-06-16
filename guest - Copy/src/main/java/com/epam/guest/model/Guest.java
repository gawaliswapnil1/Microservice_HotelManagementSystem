package com.epam.guest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="guestId")
	private Integer guestId;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="address")
	private String address;
	
	@Column(name="details")
	private String details;
	
	@Column(name="role")
	private String role;
	
	@Column(name="password")
	private String password;
	
}

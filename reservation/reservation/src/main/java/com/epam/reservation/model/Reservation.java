package com.epam.reservation.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="reservationId")
	private Integer reservationId;
	
	@Column(name="guestId")
	private Integer guestId;

	@Column(name="hotelId")
	private Integer hotelId;
	
	@Column(name="roomId")
	private Integer roomId;
	
	@Column(name="startDate")
	private Date startDate;
	
	@Column(name="endDate")
	private Date endDate;
	
	@Column(name="totalPrice")
	private Integer totalPrice;
	
}

package com.epam.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

	private Integer roomId;
	private String name;
	private String description;
	private String roomType;
	private Integer roomRate;
}

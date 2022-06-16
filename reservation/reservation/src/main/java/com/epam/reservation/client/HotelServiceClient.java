package com.epam.reservation.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.reservation.model.Hotel;
import com.epam.reservation.model.Room;
import com.epam.reservation.model.RoomDto;

@FeignClient(name="Hotel-Service")
public interface HotelServiceClient {

	@GetMapping("/hotels/findHotelById/{Id}")
	public Optional<Hotel> getHotelbyId(@PathVariable("Id") String Id);
	
	@GetMapping("/rooms/findRoomById/{Id}")
	public Optional<Room> getRoombyId(@PathVariable("Id") String Id);
	
	@GetMapping("/hotels/all")
	public List<Hotel> getHotel();
	
	@GetMapping("/rooms/findRoomAndHotel/{hotelId}")
	public List<RoomDto> findRoomAndHotel(@PathVariable("hotelId") Integer hotelId);
}

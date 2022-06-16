package com.epam.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.hotel.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query(value="select * from Room r where r.hotel_id=?1",nativeQuery = true)
	List<Room> findHotelAndRoomById(Integer hotelId);
}

package com.epam.reservation.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.reservation.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

	@Query("SELECT r FROM Reservation r WHERE r.hotelId=?1 and r.roomId=?2 and "
			+ "(r.startDate <= ?3 AND r.endDate >= ?3) "
			+ "OR (r.startDate < ?4 AND r.endDate >= ?4 )"
			+ "OR (?3 <= r.startDate AND ?4 >= r.startDate) ")
	List<Reservation> findRoomAvailabilityByDate(Integer hotelId,Integer roomId,Date startDate,Date endDate);
}

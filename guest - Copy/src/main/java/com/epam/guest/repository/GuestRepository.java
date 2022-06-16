package com.epam.guest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.guest.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer>{

	@Query(value="select * from Guest g where g.first_name=?1 and g.password=?2",nativeQuery = true)
	Optional<Guest> findGuestByUserNameAndPassword(String firstName, String password);

	
}

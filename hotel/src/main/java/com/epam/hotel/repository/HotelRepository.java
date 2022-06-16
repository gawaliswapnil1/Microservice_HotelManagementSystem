package com.epam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.hotel.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}

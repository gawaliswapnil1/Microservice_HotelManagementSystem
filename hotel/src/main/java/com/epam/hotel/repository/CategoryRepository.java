package com.epam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.hotel.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
